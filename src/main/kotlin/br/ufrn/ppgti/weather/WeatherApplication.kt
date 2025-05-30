package br.ufrn.ppgti.weather

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
class WeatherApplication

fun main(args: Array<String>) {
	runApplication<WeatherApplication>(*args)
}
