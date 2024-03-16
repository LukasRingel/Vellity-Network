package net.vellity.service.babbel

object BabbelClientFactory {
  fun create(configuration: BabbelClientConfiguration): BabbelClient {
    return BabbelClient(configuration)
  }
}