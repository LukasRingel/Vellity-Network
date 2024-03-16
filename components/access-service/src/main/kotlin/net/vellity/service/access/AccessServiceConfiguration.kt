package net.vellity.service.access

import net.vellity.utils.configuration.Environment
import net.vellity.utils.mysql.MySqlConnectionConfiguration
import net.vellity.utils.redis.RedisConnectionConfiguration

class AccessServiceConfiguration {
  fun redis(): RedisConnectionConfiguration {
    return RedisConnectionConfiguration(
      Environment.getOrDefault("REDIS_HOST", "database.vellity.net"),
      Environment.getAsIntOrDefault("REDIS_PORT", 7000),
      Environment.getOrDefault("REDIS_PASSWORD", "secret")
    )
  }

  fun mysql(): MySqlConnectionConfiguration {
    return MySqlConnectionConfiguration(
      Environment.getOrDefault("MYSQL_HOST", "localhost"),
      Environment.getAsIntOrDefault("MYSQL_PORT", 3306),
      Environment.getOrDefault("MYSQL_DATABASE", "vellity"),
      Environment.getOrDefault("MYSQL_USER", "service-access"),
      Environment.getOrDefault("MYSQL_PASSWORD", "secret")
    )
  }
}