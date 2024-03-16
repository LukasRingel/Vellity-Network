package net.vellity.service.friends

object FriendsClientFactory {
  fun create(configuration: FriendsClientConfiguration): FriendsClient {
    return FriendsClient(configuration)
  }
}