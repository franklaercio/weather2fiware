package br.ufrn.ppgti.weather.domain.repositories

import br.ufrn.ppgti.weather.domain.models.WeatherForecast
import br.ufrn.ppgti.weather.domain.models.SubscriptionRequest

interface OrionRepository {
    suspend fun publishPrecipitationData(forecast: WeatherForecast)

    suspend fun publishTemperatureData(forecast: WeatherForecast)

    suspend fun publishRelativeHumidityData(forecast: WeatherForecast)

    suspend fun publishRainData(forecast: WeatherForecast)

    suspend fun createSubscription(subscription: SubscriptionRequest)
}