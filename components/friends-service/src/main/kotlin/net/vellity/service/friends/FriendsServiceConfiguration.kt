package net.vellity.service.friends

import net.vellity.utils.configuration.Environment
import net.vellity.utils.mysql.MySqlConnectionConfiguration
import net.vellity.utils.redis.RedisConnectionConfiguration

class FriendsServiceConfiguration {
  fun redis() = RedisConnectionConfiguration(
    Environment.getOrDefault("REDIS_HOST", "database.vellity.net"),
    Environment.getAsIntOrDefault("REDIS_PORT", 7000),
    Environment.getOrDefault("REDIS_PASSWORD", "secret")
  )

  fun mysql() = MySqlConnectionConfiguration(
    Environment.getOrDefault("MYSQL_HOST", "localhost"),
    Environment.getAsIntOrDefault("MYSQL_PORT", 3306),
    Environment.getOrDefault("MYSQL_DATABASE", "vellity"),
    Environment.getOrDefault("MYSQL_USER", "friends-access"),
    Environment.getOrDefault("MYSQL_PASSWORD", "secret")
  )

  fun getDefaultFriendshipQueryLimit() = Environment.getAsIntOrDefault("FRIENDS_DEFAULT_QUERY_LIMIT", 250)

  fun getRequestActiveUntilDays() = Environment.getAsLongOrDefault("FRIENDS_REQUEST_ACTIVE_UNTIL_DAYS", 7)
}