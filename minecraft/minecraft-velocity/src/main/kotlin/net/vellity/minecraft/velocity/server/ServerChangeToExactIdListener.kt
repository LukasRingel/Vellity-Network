package net.vellity.minecraft.velocity.server

import com.velocitypowered.api.proxy.ProxyServer
import net.vellity.minecraft.common.player.ServerChangeToExactIdRequest
import net.vellity.minecraft.velocity.communcation.Listener
import net.vellity.utils.configuration.fromJson

class ServerChangeToExactIdListener(private val proxyServer: ProxyServer) : Listener {
  override fun handle(message: String) {
    val request = fromJson(message, ServerChangeToExactIdRequest::class.java)
    proxyServer.getPlayer(request.player).ifPresent { player ->
      proxyServer.getServer(request.serverId).ifPresent { server ->
        player.createConnectionRequest(server).fireAndForget()
      }
    }
  }
}