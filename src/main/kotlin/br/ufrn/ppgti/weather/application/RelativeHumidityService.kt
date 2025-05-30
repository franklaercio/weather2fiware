package br.ufrn.ppgti.weather.application

import br.ufrn.ppgti.weather.domain.enums.LocationEnum
import br.ufrn.ppgti.weather.domain.repositories.OpenMeteoRepository
import br.ufrn.ppgti.weather.domain.repositories.OrionRepository
import org.springframework.stereotype.Service

@Service
class RelativeHumidityService(
    private val openMeteoRepository: OpenMeteoRepository,
    private val orionRepository: OrionRepository
) {

    suspend fun execute(location: LocationEnum) {
        val forecast = openMeteoRepository.getRelativeHumidityForecast(location)
        orionRepository.publishRelativeHumidityData(forecast)
    }
}