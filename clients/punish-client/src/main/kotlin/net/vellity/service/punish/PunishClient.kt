package net.vellity.service.punish

import net.vellity.utils.httpclient.HttpClientFactory

class PunishClient(configuration: PunishClientConfiguration) {
  private val httpClient = HttpClientFactory.create(
    configuration.hostname,
    configuration.authKey
  )

  private val punishmentRequests = httpClient.create(PunishmentRequests::class.java)
  private val reasonRequests = httpClient.create(ReasonRequests::class.java)

  fun punishments(): PunishmentRequests =
    punishmentRequests

  fun reasons(): ReasonRequests =
    reasonRequests
}