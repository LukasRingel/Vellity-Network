package net.vellity.minecraft.velocity.login.usercache

import com.velocitypowered.api.proxy.ProxyServer
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.usercacheClient
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class UserSessionKeepAliveTask(private val proxyServer: ProxyServer) {
  fun startUpdateTask() {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
      val uuids = proxyServer.allPlayers.map { player -> player.uniqueId }
      if (uuids.isEmpty()) {
        return@scheduleAtFixedRate
      }
      usercacheClient.sessions().keepSessionsAlive(context, uuids).execute()
    }, 5, 5, TimeUnit.SECONDS)
  }
}