package net.vellity.service.explorer

import net.vellity.utils.httpserver.spring.RestAuthKeyHeaderHandler
import net.vellity.utils.httpserver.spring.RestExceptionHandler
import net.vellity.utils.redis.RedisConnection
import net.vellity.utils.redis.RedisConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class ExplorerServerBeans {
  private val configuration = ExplorerServiceConfiguration()

  @Bean
  open fun redis(): RedisConnection {
    return RedisConnectionFactory.create(configuration.redis())
  }

  @Bean
  open fun configuration(): ExplorerServiceConfiguration {
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