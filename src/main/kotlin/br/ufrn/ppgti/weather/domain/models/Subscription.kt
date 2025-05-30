package br.ufrn.ppgti.weather.domain.models

data class SubscriptionRequest(
    val description: String,
    val subject: Subject,
    val notification: Notification,
    val throttling: Int
)

data class Subject(
    val entities: List<Entity>
)

data class Entity(
    val idPattern: String,
    val type: String
)

data class Notification(
    val http: Http,
    val attrs: List<String>,
    val metadata: List<String>
)

data class Http(
    val url: String
)