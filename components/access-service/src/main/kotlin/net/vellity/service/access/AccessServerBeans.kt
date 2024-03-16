package net.vellity.service.access

import com.google.gson.Gson
import net.vellity.service.access.group.log.mysql.GroupChangeTableCreationMigration
import net.vellity.service.access.group.mysql.AccessGroupTableCreationMigration
import net.vellity.service.access.user.log.mysql.UserChangeTableCreationMigration
import net.vellity.service.access.user.mysql.AccessUserTableCreationMigration
import net.vellity.utils.httpserver.spring.RestAuthKeyHeaderHandler
import net.vellity.utils.httpserver.spring.RestExceptionHandler
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.MySqlConnectionFactory
import net.vellity.utils.redis.RedisConnection
import net.vellity.utils.redis.RedisConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class AccessServerBeans {

  private val configuration = AccessServiceConfiguration()

  @Bean
  open fun redis(): RedisConnection {
    return RedisConnectionFactory.create(configuration.redis())
  }

  @Bean
  open fun mySqlConnection(): MySqlConnection {
    val mySqlConnection = MySqlConnectionFactory.createMySqlConnection(configuration.mysql())
    mySqlConnection.connect()
    mySqlConnection.runMigration(AccessGroupTableCreationMigration())
    mySqlConnection.runMigration(AccessUserTableCreationMigration())
    mySqlConnection.runMigration(UserChangeTableCreationMigration())
    mySqlConnection.runMigration(GroupChangeTableCreationMigration())
    return mySqlConnection
  }

  @Bean
  open fun configuration(): AccessServiceConfiguration {
    return configuration
  }

  @Bean
  open fun gson(): Gson {
    return Gson()
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