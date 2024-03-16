package net.vellity.service.babbel

import net.vellity.service.babbel.translation.repository.RepositoryConfiguration
import net.vellity.utils.configuration.Environment
import net.vellity.utils.mysql.MySqlConnectionConfiguration
import net.vellity.utils.redis.RedisConnectionConfiguration
import java.io.File

class BabbelServiceConfiguration {
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
      Environment.getOrDefault("MYSQL_USER", "babbel-access"),
      Environment.getOrDefault("MYSQL_PASSWORD", "secret")
    )
  }

  fun repository(): RepositoryConfiguration {
    return RepositoryConfiguration(
      Environment.getOrDefault("BABBEL_REPOSITORY_HOST", "https://git.vellity.net/locales.git"),
      Environment.getOrDefault("BABBEL_REPOSITORY_USERNAME", "git"),
      Environment.getOrDefault("BABBEL_REPOSITORY_PASSWORD", "secret"),
      Environment.getOrDefault("BABBEL_REPOSITORY_BRANCH", "main"),
      Environment.getOrDefault(
        "BABBEL_REPOSITORY_LOCAL_PATH",
        System.getenv("APPDATA") + File.separator + "vellity" + File.separator + "locales"
      )
    )
  }
}