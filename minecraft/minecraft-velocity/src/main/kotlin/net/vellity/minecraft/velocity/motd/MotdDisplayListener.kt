package net.vellity.minecraft.velocity.motd

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyPingEvent
import com.velocitypowered.api.proxy.server.ServerPing

class MotdDisplayListener {
  @Subscribe
  fun displayMotd(event: ProxyPingEvent) {
    val builder = severPingBuilder(event)
    event.ping.favicon.ifPresent { builder.favicon(it) }
    event.ping = builder.build()
  }

  private fun severPingBuilder(event: ProxyPingEvent): ServerPing.Builder =
    ServerPing.builder()
      .description(MotDService.motdDisplay)
      .maximumPlayers(MotDService.slots)
      .version(event.ping.version)
}