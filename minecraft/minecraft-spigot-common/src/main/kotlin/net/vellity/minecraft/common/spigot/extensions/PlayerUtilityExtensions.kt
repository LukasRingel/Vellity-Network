package net.vellity.minecraft.common.spigot.extensions

import net.vellity.minecraft.common.player.ServerChangeToExactIdRequest
import net.vellity.minecraft.common.spigot.communication.ProxyCommunicationService
import org.bukkit.entity.Player

object PlayerUtilityExtensions {
  fun Player.sendToServer(id: String) {
    ProxyCommunicationService.sendToAllProxiesOfCurrentContext(
      ServerChangeToExactIdRequest.TOPIC,
      ServerChangeToExactIdRequest(this.uniqueId, id)
    )
  }
}