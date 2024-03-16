package net.vellity.service.babbel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [RedisAutoConfiguration::class, DataSourceAutoConfiguration::class])
open class BabbelService

fun main(args: Array<String>) {
  runApplication<BabbelService>(*args)
}