package br.ufrn.ppgti.weather.infrastructure.configs

import feign.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignClientConfig {
    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL // Pode ser BASIC, HEADERS ou FULL
    }
}
