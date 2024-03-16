package net.vellity.service.explorer

import net.vellity.utils.configuration.Environment
import net.vellity.utils.redis.RedisConnectionConfiguration

class ExplorerServiceConfiguration {
  fun redis(): RedisConnectionConfiguration {
    return RedisConnectionConfiguration(
      Environment.getOrDefault("REDIS_HOST", "database.vellity.net"),
      Environment.getAsIntOrDefault("REDIS_PORT", 7000),
      Environment.getOrDefault("REDIS_PASSWORD", "secret")
    )
  }
}