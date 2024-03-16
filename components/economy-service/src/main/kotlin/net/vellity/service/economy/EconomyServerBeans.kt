package net.vellity.service.economy

import net.vellity.service.economy.currency.mysql.CurrencyTableCreationMigration
import net.vellity.service.economy.user.mysql.UserTableCreationMigration
import net.vellity.utils.httpserver.spring.RestAuthKeyHeaderHandler
import net.vellity.utils.httpserver.spring.RestExceptionHandler
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.MySqlConnectionFactory
import net.vellity.utils.redis.RedisConnection
import net.vellity.utils.redis.RedisConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class EconomyServerBeans {
  private val configuration = EconomyServiceConfiguration()

  @Bean
  open fun redis(): RedisConnection {
    return RedisConnectionFactory.create(configuration.redis())
  }

  @Bean
  open fun mySqlConnection(): MySqlConnection {
    val mySqlConnection = MySqlConnectionFactory.createMySqlConnection(configuration.mysql())
    mySqlConnection.connect()
    mySqlConnection.runMigration(CurrencyTableCreationMigration())
    mySqlConnection.runMigration(UserTableCreationMigration())
    return mySqlConnection
  }

  @Bean
  open fun configuration(): EconomyServiceConfiguration {
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