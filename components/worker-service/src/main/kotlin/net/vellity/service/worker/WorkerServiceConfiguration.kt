package net.vellity.service.worker

import net.vellity.utils.configuration.Environment
import net.vellity.utils.mysql.MySqlConnectionConfiguration
import net.vellity.utils.redis.RedisConnectionConfiguration

class WorkerServiceConfiguration {
  fun redis() = RedisConnectionConfiguration(
    Environment.getOrDefault("REDIS_HOST", "database.vellity.net"),
    Environment.getAsIntOrDefault("REDIS_PORT", 7000),
    Environment.getOrDefault("REDIS_PASSWORD", "secret")
  )

  fun mysql() = MySqlConnectionConfiguration(
    Environment.getOrDefault("MYSQL_HOST", "localhost"),
    Environment.getAsIntOrDefault("MYSQL_PORT", 3306),
    Environment.getOrDefault("MYSQL_DATABASE", "vellity"),
    Environment.getOrDefault("MYSQL_USER", "worker-access"),
    Environment.getOrDefault("MYSQL_PASSWORD", "secret")
  )
}