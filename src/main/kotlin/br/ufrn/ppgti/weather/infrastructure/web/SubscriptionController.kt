package br.ufrn.ppgti.weather.infrastructure.web

import br.ufrn.ppgti.weather.application.SubscriptionService
import br.ufrn.ppgti.weather.domain.models.SubscriptionRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subscriptions")
class SubscriptionController(
    private val subscriptionService: SubscriptionService
) {

    @PostMapping
    suspend fun subscribe(@RequestBody request: SubscriptionRequest): ResponseEntity<String> {
        subscriptionService.createDefaultSubscription(request)
        return ResponseEntity.ok("Subscription sent to Orion")
    }
}
