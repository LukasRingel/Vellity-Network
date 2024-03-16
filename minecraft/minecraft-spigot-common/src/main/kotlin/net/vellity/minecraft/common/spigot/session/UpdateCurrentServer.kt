package net.vellity.minecraft.common.spigot.session

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.globalThreadPool
import net.vellity.minecraft.common.identity
import net.vellity.minecraft.common.usercacheClient
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class UpdateCurrentServer : Listener {
  @EventHandler
  private fun updateCurrentServer(event: PlayerJoinEvent) {
    globalThreadPool.submit {
      usercacheClient.sessions()
        .updateCurrentServer(context, event.player.uniqueId, identity.id)
        .execute()
    }
  }
}