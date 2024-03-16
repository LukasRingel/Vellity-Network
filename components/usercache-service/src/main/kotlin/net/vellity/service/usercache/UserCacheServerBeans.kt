package net.vellity.service.usercache

import net.vellity.service.usercache.login.mysql.LoginTablesCreateMigration
import net.vellity.service.usercache.messages.mysql.MessagesTablesCreationMigration
import net.vellity.service.usercache.names.mysql.NamesTablesCreateMigration
import net.vellity.service.usercache.notifications.mysql.NotificationTablesCreationMigration
import net.vellity.service.usercache.textures.mysql.TexturesTablesCreationMigration
import net.vellity.utils.httpserver.spring.RestAuthKeyHeaderHandler
import net.vellity.utils.httpserver.spring.RestExceptionHandler
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.MySqlConnectionFactory
import net.vellity.utils.redis.RedisConnection
import net.vellity.utils.redis.RedisConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class UserCacheServerBeans {
  private val configuration = UserCacheServiceConfiguration()

  @Bean
  open fun redis(): RedisConnection {
    return RedisConnectionFactory.create(configuration.redis())
  }

  @Bean
  open fun mySqlConnection(): MySqlConnection {
    val mySqlConnection = MySqlConnectionFactory.createMySqlConnection(configuration.mysql())
    mySqlConnection.connect()
    mySqlConnection.runMigration(LoginTablesCreateMigration())
    mySqlConnection.runMigration(NamesTablesCreateMigration())
    mySqlConnection.runMigration(TexturesTablesCreationMigration())
    mySqlConnection.runMigration(NotificationTablesCreationMigration())
    mySqlConnection.runMigration(MessagesTablesCreationMigration())
    return mySqlConnection
  }

  @Bean
  open fun configuration(): UserCacheServiceConfiguration {
    return configuration
  }

  @Bean
  open fun exceptionHandler(): RestExceptionHandler {
    return RestExceptionHandler()
  }

  @Bean
  open fun apiKeyFilter(): RestAuthKeyHeaderHandler {
    return RestAuthKeyHeaderHandler()
  }
}