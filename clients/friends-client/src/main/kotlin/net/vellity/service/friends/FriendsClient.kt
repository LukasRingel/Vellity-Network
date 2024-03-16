package net.vellity.service.friends

import net.vellity.utils.httpclient.HttpClientFactory

class FriendsClient(configuration: FriendsClientConfiguration) {
  private val httpClient = HttpClientFactory.create(
    configuration.hostname,
    configuration.authKey
  )

  private val friendshipRequests: FriendshipRequests = httpClient.create(FriendshipRequests::class.java)
  private val profileRequests: ProfileRequests = httpClient.create(ProfileRequests::class.java)
  private val settingsRequests: SettingsRequests = httpClient.create(SettingsRequests::class.java)

  fun friendships(): FriendshipRequests {
    return friendshipRequests
  }

  fun profiles(): ProfileRequests {
    return profileRequests
  }

  fun settings(): SettingsRequests {
    return settingsRequests
  }
}