package br.ufrn.ppgti.weather.application

import br.ufrn.ppgti.weather.domain.models.SubscriptionRequest
import br.ufrn.ppgti.weather.domain.repositories.OrionRepository
import org.springframework.stereotype.Service

@Service
class SubscriptionService(
    private val orionRepository: OrionRepository
) {

    suspend fun createDefaultSubscription(request: SubscriptionRequest) {
        orionRepository.createSubscription(request)
    }
}
