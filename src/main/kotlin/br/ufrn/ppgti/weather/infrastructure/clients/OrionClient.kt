package br.ufrn.ppgti.weather.infrastructure.clients

import br.ufrn.ppgti.weather.domain.models.SubscriptionRequest
import br.ufrn.ppgti.weather.infrastructure.clients.dtos.FiwareAttribute
import br.ufrn.ppgti.weather.infrastructure.clients.dtos.WeatherEntity
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@FeignClient(name = "fiwareOrionApi", url = "\${orion.api.url}")
interface OrionClient {

    @PostMapping("/v2/entities")
    suspend fun createOrUpdateEntity(
        @RequestBody entity: WeatherEntity,
        @RequestHeader("Fiware-Service") service: String,
        @RequestHeader("Fiware-ServicePath") servicePath: String
    )

    @GetMapping("/v2/entities/{entityId}")
    suspend fun getEntity(
        @PathVariable entityId: String,
        @RequestHeader("Fiware-Service") service: String,
        @RequestHeader("Fiware-ServicePath") servicePath: String
    ): WeatherEntity

    @PutMapping("/v2/entities/{entityId}/attrs")
    suspend fun updateEntityAttributes(
        @PathVariable entityId: String,
        @RequestBody attributes: Map<String, FiwareAttribute>,
        @RequestHeader("Fiware-Service") service: String,
        @RequestHeader("Fiware-ServicePath") servicePath: String
    )

    @PostMapping(
        value = ["/subscriptions"],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createSubscription(
        @RequestBody request: SubscriptionRequest,
        @RequestHeader("Fiware-Service") service: String = "weather",
        @RequestHeader("Fiware-ServicePath") servicePath: String = "/forecast"
    )
}