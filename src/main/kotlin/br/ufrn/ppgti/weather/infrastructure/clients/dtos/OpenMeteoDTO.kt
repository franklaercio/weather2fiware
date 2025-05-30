package br.ufrn.ppgti.weather.infrastructure.clients.dtos

data class PrecipitationResponse(
    val hourly: PrecipitationData?
)

data class PrecipitationData(
    val time: List<String>?,
    val precipitation_probability: List<Double>?
)

data class TemperatureResponse(
    val hourly: TemperatureData?
)

data class TemperatureData(
    val time: List<String>?,
    val temperature_2m: List<Double>?
)

data class RelativeHumidityResponse(
    val hourly: RelativeHumidityData?
)

data class RelativeHumidityData(
    val time: List<String>?,
    val relative_humidity_2m: List<Double>?
)

data class RainResponse(
    val hourly: RainData?
)

data class RainData(
    val time: List<String>?,
    val rain: List<Double>?
)