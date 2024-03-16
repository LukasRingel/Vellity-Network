package net.vellity.service.config

object ConfigClientFactory {
  fun create(configuration: ConfigClientConfiguration): ConfigClient {
    return ConfigClient(configuration)
  }
}