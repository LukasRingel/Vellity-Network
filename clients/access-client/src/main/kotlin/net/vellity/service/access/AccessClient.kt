package net.vellity.service.access

import net.vellity.utils.httpclient.HttpClientFactory

class AccessClient(configuration: AccessClientConfiguration) {

  private val httpClient = HttpClientFactory.create(
    configuration.hostname,
    configuration.authKey
  )

  private val userRequests: UserRequests = httpClient.create(UserRequests::class.java)
  private val groupRequests: GroupRequests = httpClient.create(GroupRequests::class.java)

  fun users(): UserRequests {
    return userRequests
  }

  fun groups(): GroupRequests {
    return groupRequests
  }
}