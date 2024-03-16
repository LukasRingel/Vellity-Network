package net.vellity.service.punish

object PunishClientFactory {
  fun create(configuration: PunishClientConfiguration): PunishClient {
    return PunishClient(configuration)
  }
}