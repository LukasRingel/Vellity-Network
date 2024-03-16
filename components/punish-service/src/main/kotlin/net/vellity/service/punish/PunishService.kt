package net.vellity.service.punish

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [RedisAutoConfiguration::class, DataSourceAutoConfiguration::class])
open class PunishService

fun main(args: Array<String>) {
  runApplication<PunishService>(*args)
}