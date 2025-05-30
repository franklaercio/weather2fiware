package br.ufrn.ppgti.weather.domain.models

import br.ufrn.ppgti.weather.domain.enums.LocationEnum

data class WeatherForecast(
    val location: LocationEnum,
    val probabilities: List<ForecastData>
)
