package net.vellity.service.guardian

object GuardianClientFactory {
  fun create(configuration: GuardianClientConfiguration): GuardianClient {
    return GuardianClient(configuration)
  }
}