package net.vellity.service.config

import net.vellity.utils.httpclient.HttpClientFactory

class ConfigClient(configuration: ConfigClientConfiguration) {
  private val httpClient = HttpClientFactory.create(
    configuration.hostname,
    configuration.authKey
  )

  private val featureFlagRequests: FeatureFlagRequests = httpClient.create(FeatureFlagRequests::class.java)
  private val configRequests: ConfigRequests = httpClient.create(ConfigRequests::class.java)

  fun featureFlags(): FeatureFlagRequests {
    return featureFlagRequests
  }

  fun configs(): ConfigRequests {
    return configRequests
  }
}