package net.vellity.service.guardian

import net.vellity.utils.httpclient.HttpClientFactory

class GuardianClient(configuration: GuardianClientConfiguration) {
  private val httpClient = HttpClientFactory.create(
    configuration.hostname,
    configuration.authKey
  )

  private val reportRequests: ReportRequests = httpClient.create(ReportRequests::class.java)
  private val chatlogRequests: ChatlogRequests = httpClient.create(ChatlogRequests::class.java)

  fun reports(): ReportRequests {
    return reportRequests
  }

  fun chatlogs(): ChatlogRequests {
    return chatlogRequests
  }
}