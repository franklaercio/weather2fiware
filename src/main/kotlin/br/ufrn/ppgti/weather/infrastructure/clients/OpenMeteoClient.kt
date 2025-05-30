package br.ufrn.ppgti.weather.infrastructure.clients

import br.ufrn.ppgti.weather.infrastructure.clients.dtos.PrecipitationResponse
import br.ufrn.ppgti.weather.infrastructure.clients.dtos.RainResponse
import br.ufrn.ppgti.weather.infrastructure.clients.dtos.RelativeHumidityResponse
import br.ufrn.ppgti.weather.infrastructure.clients.dtos.TemperatureResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "openMeteoClient", url = "\${weather.api.url}")
interface OpenMeteoClient {

    @GetMapping("/forecast")
    fun getForecast(
        @RequestParam("latitude") latitude: Double,
        @RequestParam("longitude") longitude: Double,
        @RequestParam("hourly") hourly: String = "precipitation_probability",
        @RequestParam("timezone") timezone: String = "America/Sao_Paulo",
        @RequestParam("forecast_days") forecastDays: Int,
    ): PrecipitationResponse

    @GetMapping("/forecast")
    fun getTemperature(
        @RequestParam("latitude") latitude: Double,
        @RequestParam("longitude") longitude: Double,
        @RequestParam("hourly") hourly: String = "temperature_2m",
        @RequestParam("timezone") timezone: String = "America/Sao_Paulo",
        @RequestParam("forecast_days") forecastDays: Int,
    ): TemperatureResponse

    @GetMapping("/forecast")
    fun getRelativeHumidity(
        @RequestParam("latitude") latitude: Double,
        @RequestParam("longitude") longitude: Double,
        @RequestParam("hourly") hourly: String = "relative_humidity_2m",
        @RequestParam("timezone") timezone: String = "America/Sao_Paulo",
        @RequestParam("forecast_days") forecastDays: Int,
    ): RelativeHumidityResponse

    @GetMapping("/forecast")
    fun getRain(
        @RequestParam("latitude") latitude: Double,
        @RequestParam("longitude") longitude: Double,
        @RequestParam("hourly") hourly: String = "rain",
        @RequestParam("timezone") timezone: String = "America/Sao_Paulo",
        @RequestParam("forecast_days") forecastDays: Int,
    ): RainResponse
}
