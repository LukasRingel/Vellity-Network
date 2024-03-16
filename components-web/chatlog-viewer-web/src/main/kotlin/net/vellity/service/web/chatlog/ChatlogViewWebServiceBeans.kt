package net.vellity.service.web.chatlog

import net.vellity.service.guardian.GuardianClient
import net.vellity.service.guardian.GuardianClientConfiguration
import net.vellity.service.guardian.GuardianClientFactory
import net.vellity.service.usercache.UserCacheClient
import net.vellity.service.usercache.UserCacheClientConfiguration
import net.vellity.service.usercache.UserCacheClientFactory
import net.vellity.utils.configuration.Environment
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.MySqlConnectionFactory
import net.vellity.utils.redis.RedisConnection
import net.vellity.utils.redis.RedisConnectionFactory
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ChatlogViewWebServiceBeans {
  private val configuration = ChatlogViewWebServiceConfiguration()

  @Bean
  open fun redis(): RedisConnection {
    return RedisConnectionFactory.create(configuration.redis())
  }

  @Bean
  open fun mySqlConnection(): MySqlConnection {
    val mySqlConnection = MySqlConnectionFactory.createMySqlConnection(configuration.mysql())
    mySqlConnection.connect()
    return mySqlConnection
  }

  @Bean
  open fun configuration(): ChatlogViewWebServiceConfiguration {
    return configuration
  }

  @Bean
  open fun userCacheClient(): UserCacheClient {
    return UserCacheClientFactory.create(UserCacheClientConfiguration(
      Environment.getOrDefault("USERCACHE_HOST", "https://api.vellity.net/usercache/"),
      Environment.getOrDefault("USERCACHE_AUTH_KEY", "123456789")
    ))
  }

  @Bean
  open fun guardianClient(): GuardianClient {
    return GuardianClientFactory.create(
      GuardianClientConfiguration(
      Environment.getOrDefault("GUARDIAN_HOST", "https://api.vellity.net/guardian/"),
      Environment.getOrDefault("GUARDIAN_AUTH_KEY", "123456789")
    )
    )
  }
}