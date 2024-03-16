package net.vellity.service.guardian

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [RedisAutoConfiguration::class, DataSourceAutoConfiguration::class])
open class GuardianService

fun main(args: Array<String>) {
  runApplication<GuardianService>(*args)
}