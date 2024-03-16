package net.vellity.service.economy

object EconomyClientFactory {
  fun create(configuration: EconomyClientConfiguration): EconomyClient {
    return EconomyClient(configuration)
  }
}