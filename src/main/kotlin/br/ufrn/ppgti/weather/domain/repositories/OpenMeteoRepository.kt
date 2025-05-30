package br.ufrn.ppgti.weather.domain.repositories

import br.ufrn.ppgti.weather.domain.enums.LocationEnum
import br.ufrn.ppgti.weather.domain.models.WeatherForecast

interface OpenMeteoRepository {
    suspend fun getPrecipitationForecast(location: LocationEnum): WeatherForecast

    suspend fun getTemperatureForecast(location: LocationEnum): WeatherForecast

    suspend fun getRelativeHumidityForecast(location: LocationEnum): WeatherForecast

    suspend fun getRainForecast(location: LocationEnum): WeatherForecast
}