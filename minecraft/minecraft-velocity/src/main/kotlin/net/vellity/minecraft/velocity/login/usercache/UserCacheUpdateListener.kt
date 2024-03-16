package net.vellity.minecraft.velocity.login.usercache

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.DisconnectEvent
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.globalThreadPool
import net.vellity.minecraft.common.identity
import net.vellity.minecraft.common.usercacheClient
import net.vellity.minecraft.velocity.login.events.NetworkLoginEvent

class UserCacheUpdateListener {
  @Subscribe
  private fun updateUserCache(event: NetworkLoginEvent) {
    globalThreadPool.submit {
      try {
        sendLoginToUserCache(event)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  private fun sendLoginToUserCache(event: NetworkLoginEvent) {
    event.player.gameProfile.properties.find { it.name == "textures" }?.let { texture ->
      usercacheClient.combined().handleLoginWithDetails(
        context,
        event.player.uniqueId,
        event.player.username,
        identity.id,
        event.player.remoteAddress.hostString,
        texture.signature,
        texture.value
      ).execute()
    }
  }

  @Subscribe
  private fun destroySession(event: DisconnectEvent) {
    globalThreadPool.submit {
      usercacheClient.logins().registerLogout(context, event.player.uniqueId).execute()
    }
  }
}