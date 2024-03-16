package net.vellity.utils.redis

import net.vellity.utils.configuration.ConfigurationFactory

object RedisConnectionFactory {

  fun create(config: RedisConnectionConfiguration): RedisConnection {
    return RedisConnection(config)
  }

  fun create(pathToConfigurationFile: String): RedisConnection {
    return create(
      ConfigurationFactory
        .create(
          pathToConfigurationFile,
          RedisConnectionConfiguration::class.java
        ).get()
    )
  }
}