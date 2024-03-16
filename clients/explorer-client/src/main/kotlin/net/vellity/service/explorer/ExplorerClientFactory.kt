package net.vellity.service.explorer

object ExplorerClientFactory {
  fun create(configuration: ExplorerClientConfiguration): ExplorerClient {
    return ExplorerClient(configuration)
  }
}