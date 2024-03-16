package net.vellity.minecraft.velocity.server

import com.velocitypowered.api.event.Continuation
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent
import com.velocitypowered.api.proxy.ProxyServer
import net.vellity.minecraft.common.globalThreadPool
import net.vellity.minecraft.velocity.server.choose.LowestPlayersChooseAlgorithm

class ServerChooseListener(private val proxyServer: ProxyServer) {
  @Subscribe
  fun chooseInitialServer(event: PlayerChooseInitialServerEvent, continuation: Continuation) {
    globalThreadPool.submit {
      try {
        val groupForPlayer = InitialServerService.getInitialServerGroupForPlayer(event.player.uniqueId)
        event.setInitialServer(chooseAlgorithm.best(findAllServers(groupForPlayer)))
        continuation.resume()
      } catch (e: Exception) {
        continuation.resumeWithException(e)
      }
    }
  }

  private fun findAllServers(group: String) =
    proxyServer.allServers.filter { server ->
      group == server.serverInfo.name.replace(
        "-" + server.serverInfo.name.split("-").last(),
        ""
      )
    }

  companion object {
    private val chooseAlgorithm = LowestPlayersChooseAlgorithm()
  }
}