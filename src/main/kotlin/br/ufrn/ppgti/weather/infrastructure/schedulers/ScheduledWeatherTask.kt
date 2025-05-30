package br.ufrn.ppgti.weather.infrastructure.schedulers

import br.ufrn.ppgti.weather.application.PrecipitationService
import br.ufrn.ppgti.weather.application.RainService
import br.ufrn.ppgti.weather.application.RelativeHumidityService
import br.ufrn.ppgti.weather.application.TemperatureService
import br.ufrn.ppgti.weather.domain.enums.LocationEnum
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledWeatherTask(
    private val precipitationService: PrecipitationService,
    private val rainService: RainService,
    private val relativeHumidityService: RelativeHumidityService,
    private val temperatureService: TemperatureService,
) {
    private val logger = LoggerFactory.getLogger(ScheduledWeatherTask::class.java)

    @Scheduled(cron = "0 0 * * * ?") // Every hour at the start of the hour
    fun updateWeatherData() {
        logger.info("Spring Scheduled Task: Starting job...")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                precipitationService.execute(LocationEnum.NATAL)
                logger.info("Spring Scheduled Task: Successfully fetched and published weather data.")
            } catch (e: Exception) {
                logger.error("Spring Scheduled Task: Error during scheduled weather update.", e)
            }
        }
    }

    @Scheduled(cron = "0 0 * * * ?") // Every hour at the start of the hour
    fun updateRainData() {
        logger.info("Spring Scheduled Task: Starting rain data job...")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                rainService.execute(LocationEnum.NATAL)
                logger.info("Spring Scheduled Task: Successfully fetched and published rain data.")
            } catch (e: Exception) {
                logger.error("Spring Scheduled Task: Error during scheduled rain update.", e)
            }
        }
    }

    @Scheduled(cron = "0 0 * * * ?") // Every hour at the start of the hour
    fun updateRelativeHumidityData() {
        logger.info("Spring Scheduled Task: Starting relative humidity data job...")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                relativeHumidityService.execute(LocationEnum.NATAL)
                logger.info("Spring Scheduled Task: Successfully fetched and published relative humidity data.")
            } catch (e: Exception) {
                logger.error("Spring Scheduled Task: Error during scheduled relative humidity update.", e)
            }
        }
    }

    @Scheduled(cron = "0 0 * * * ?") // Every hour at the start of the hour
    fun updateTemperatureData() {
        logger.info("Spring Scheduled Task: Starting temperature data job...")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                temperatureService.execute(LocationEnum.NATAL)
                logger.info("Spring Scheduled Task: Successfully fetched and published temperature data.")
            } catch (e: Exception) {
                logger.error("Spring Scheduled Task: Error during scheduled temperature update.", e)
            }
        }
    }

    @PostConstruct
    fun publishOnStartup() = runBlocking {
        precipitationService.execute(LocationEnum.NATAL)
        rainService.execute(LocationEnum.NATAL)
        relativeHumidityService.execute(LocationEnum.NATAL)
        temperatureService.execute(LocationEnum.NATAL)
    }
}