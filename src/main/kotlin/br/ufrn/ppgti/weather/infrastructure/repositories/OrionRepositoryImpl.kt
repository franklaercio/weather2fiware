package br.ufrn.ppgti.weather.infrastructure.repositories

import br.ufrn.ppgti.weather.domain.enums.LocationEnum
import br.ufrn.ppgti.weather.domain.models.SubscriptionRequest
import br.ufrn.ppgti.weather.domain.models.WeatherForecast
import br.ufrn.ppgti.weather.domain.repositories.OrionRepository
import br.ufrn.ppgti.weather.infrastructure.clients.OrionClient
import br.ufrn.ppgti.weather.infrastructure.clients.dtos.FiwareAttribute
import br.ufrn.ppgti.weather.infrastructure.clients.dtos.WeatherEntity
import feign.FeignException
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Service
class OrionRepositoryImpl(
    private val orionClient: OrionClient
) : OrionRepository {

    private val logger = LoggerFactory.getLogger(OrionRepositoryImpl::class.java)

    /**
     * Helper function to dynamically build a FIWARE WeatherEntity.
     */
    private fun buildWeatherEntity(
        time: String,
        value: Double,
        location: LocationEnum,
        createdAt: String = LocalDateTime.now().toString(),
        updatedAt: String = LocalDateTime.now().toString(),
        service: String
    ): WeatherEntity {
        val localDateTime = LocalDateTime.parse(time)
        val zonedDateTimeUtc = localDateTime.atZone(ZoneOffset.UTC)
        val formattedUtcString = zonedDateTimeUtc.format(DateTimeFormatter.ISO_INSTANT)
        val entityId = "${service}:${location.name}:${formattedUtcString}"

        return WeatherEntity(
            id = entityId,
            type = service,
            city = FiwareAttribute("Text", location.name),
            timestamp = FiwareAttribute("DateTime", formattedUtcString),
            value = FiwareAttribute("Number", value),
            createdAt = FiwareAttribute("DateTime", createdAt),
            updatedAt = FiwareAttribute("DateTime", updatedAt),
            location = FiwareAttribute(
                type = "geo:json",
                value = mapOf(
                    "type" to "Point",
                    "coordinates" to listOf(location.longitude, location.latitude)
                )
            )
        )
    }

    /**
     * Generic internal function to publish forecast data.
     * @param location Forecast location.
     * @param forecast Weather forecast data containing probabilities.
     * @param service FIWARE attribute name to be published (e.g., "temperature").
     */
    private suspend fun publishData(
        location: LocationEnum,
        forecast: WeatherForecast,
        service: String,
        servicePath: String
    ) {
        if (forecast.probabilities.isEmpty()) {
            logger.info("No statistics to publish for ${location.name}, attribute $service.")
            return
        }

        for (statItem in forecast.probabilities) {
            val time = statItem.time
            val value = statItem.probability

            val entityForCreation = buildWeatherEntity(
                time = time,
                value = value ?: 0.0,
                location = location,
                service = service,
                createdAt = LocalDateTime.now().toString(),
                updatedAt = LocalDateTime.now().toString()
            )
            val entityId = entityForCreation.id

            logger.info("Publishing data for $service: $value at $time for ${location.name} (Entity ID: $entityId)")

            try {
                orionClient.getEntity(entityId, service, servicePath)

                val attributeToUpdate = FiwareAttribute("Number", value ?: 0.0)
                val attributesToUpdate = mapOf(
                    "value" to attributeToUpdate,
                    "createdAt" to FiwareAttribute("DateTime", time),
                    "updatedAt" to FiwareAttribute("DateTime", LocalDateTime.now().toString())
                )

                orionClient.updateEntityAttributes(entityId, attributesToUpdate, service, servicePath)

                logger.info("Data successfully updated for $entityId (attribute $service): $value at $time")
            } catch (ex: FeignException) {
                if (ex.status() == 404) {
                    logger.info("Entity $entityId not found. Creating new entity: $entityForCreation")
                    orionClient.createOrUpdateEntity(entityForCreation, service, servicePath)
                } else {
                    logger.error(
                        "Error interacting with Orion for entity $entityId (attribute $service): ${ex.message}",
                        ex
                    )
                    throw ex
                }
            } catch (e: Exception) {
                logger.error(
                    "Unexpected error while publishing data for $entityId (attribute $service): ${e.message}",
                    e
                )
                throw e
            }

            delay(1000)
        }
    }

    override suspend fun publishPrecipitationData(forecast: WeatherForecast) {
        logger.info("Received request to publish precipitation data for ${forecast.location.name}")
        publishData(
            location = forecast.location,
            forecast = forecast,
            service = "precipitation",
            servicePath = "/precipitation"
        )
    }

    override suspend fun publishTemperatureData(forecast: WeatherForecast) {
        logger.info("Received request to publish temperature data for ${forecast.location.name}")
        publishData(
            location = forecast.location,
            forecast = forecast,
            service = "temperature",
            servicePath = "/temperature"
        )
    }

    override suspend fun publishRelativeHumidityData(forecast: WeatherForecast) {
        logger.info("Received request to publish relative humidity data for ${forecast.location.name}")
        publishData(
            location = forecast.location,
            forecast = forecast,
            service = "relativeHumidity",
            servicePath = "/relativeHumidity"
        )
    }

    override suspend fun publishRainData(forecast: WeatherForecast) {
        logger.info("Received request to publish rain data for ${forecast.location.name}")
        publishData(
            location = forecast.location,
            forecast = forecast,
            service = "rain",
            servicePath = "/rain"
        )
    }

    override suspend fun createSubscription(subscription: SubscriptionRequest) {
        logger.info("Creating subscription in Orion: $subscription")
        try {
            orionClient.createSubscription(subscription)
            logger.info("Subscription created successfully.")
        } catch (e: Exception) {
            logger.error("Failed to create subscription: ${e.message}", e)
            throw e
        }
    }
}
