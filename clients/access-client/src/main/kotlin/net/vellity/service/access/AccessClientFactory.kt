package net.vellity.service.access

object AccessClientFactory {
  fun create(configuration: AccessClientConfiguration): AccessClient {
    return AccessClient(configuration)
  }
}