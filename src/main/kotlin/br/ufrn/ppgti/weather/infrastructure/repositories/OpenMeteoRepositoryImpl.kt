package br.ufrn.ppgti.weather.infrastructure.repositories

import br.ufrn.ppgti.weather.domain.enums.LocationEnum
import br.ufrn.ppgti.weather.domain.models.ForecastData
import br.ufrn.ppgti.weather.domain.models.WeatherForecast
import br.ufrn.ppgti.weather.domain.repositories.OpenMeteoRepository
import br.ufrn.ppgti.weather.infrastructure.clients.OpenMeteoClient
import org.springframework.stereotype.Service

@Service
class OpenMeteoRepositoryImpl(private val openMeteoClient: OpenMeteoClient) : OpenMeteoRepository {
    override suspend fun getPrecipitationForecast(location: LocationEnum): WeatherForecast {
        val response = openMeteoClient.getForecast(
            latitude = location.latitude,
            longitude = location.longitude,
            forecastDays = 1
        )

        return mapResponseToDomain(
            response.hourly?.time,
            response.hourly?.precipitation_probability,
            location
        )
    }

    override suspend fun getTemperatureForecast(location: LocationEnum): WeatherForecast {
        val response = openMeteoClient.getTemperature(
            latitude = location.latitude,
            longitude = location.longitude,
            forecastDays = 1
        )

        return mapResponseToDomain(
            response.hourly?.time,
            response.hourly?.temperature_2m,
            location
        )
    }

    override suspend fun getRelativeHumidityForecast(location: LocationEnum): WeatherForecast {
        val response = openMeteoClient.getRelativeHumidity(
            latitude = location.latitude,
            longitude = location.longitude,
            forecastDays = 1
        )

        return mapResponseToDomain(
            response.hourly?.time,
            response.hourly?.relative_humidity_2m,
            location
        )
    }

    override suspend fun getRainForecast(location: LocationEnum): WeatherForecast {
        val response = openMeteoClient.getRain(
            latitude = location.latitude,
            longitude = location.longitude,
            forecastDays = 1
        )

        return mapResponseToDomain(
            response.hourly?.time,
            response.hourly?.rain,
            location
        )
    }

    private fun mapResponseToDomain(
        times: List<String>?,
        values: List<Double>?,
        location: LocationEnum
    ): WeatherForecast {
        val data = times?.zip(values ?: emptyList())
            ?.map { (time, value) -> ForecastData(time, value) } ?: emptyList()
        return WeatherForecast(location, data)
    }

}