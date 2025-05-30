package br.ufrn.ppgti.weather.infrastructure.clients.dtos

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FiwareAttribute(
    val type: String,
    val value: Any
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class WeatherEntity(
    val id: String,
    val type: String,
    val city: FiwareAttribute,
    val timestamp: FiwareAttribute,
    val value: FiwareAttribute,
    val location: FiwareAttribute,
    val createdAt: FiwareAttribute,
    val updatedAt: FiwareAttribute
)