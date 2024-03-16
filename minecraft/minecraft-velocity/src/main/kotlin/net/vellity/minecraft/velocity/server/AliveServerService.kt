package net.vellity.minecraft.velocity.server

import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.RegisteredServer
import com.velocitypowered.api.proxy.server.ServerInfo
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.explorerClient
import net.vellity.service.explorer.Identity
import org.slf4j.Logger
import java.net.InetSocketAddress
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class AliveServerService(private val proxyServer: ProxyServer, private val logger: Logger) {
  private val staticServers = proxyServer.allServers.map { server -> server.serverInfo.name }
  private val ignoredGroupsContains = listOf("proxy")

  fun startAutomaticServerRegistration() {
    Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay({
      val aliveServers = getAliveServers()
      removeAllServersWhichAreNotAliveAndNotStatic(aliveServers)
      registerServersWhichAreNotRegistered(aliveServers)
    }, 0, 1, TimeUnit.SECONDS)
  }

  private fun registerServersWhichAreNotRegistered(aliveServers: List<Identity>) {
    aliveServers.filter { identity -> proxyServer.getServer(identity.id).isEmpty }
      .filter { identity -> ignoredGroupsContains.none { name -> identity.id.lowercase().contains(name) } }
      .forEach { identity -> registerServer(identity) }
  }

  private fun removeAllServersWhichAreNotAliveAndNotStatic(aliveServers: List<Identity>) {
    proxyServer.allServers
      .filter { server -> aliveServers.none { identity -> identity.id == server.serverInfo.name } }
      .filter { server -> staticServers.none { serverName -> serverName == server.serverInfo.name } }
      .forEach { registeredServer -> unregisterServer(registeredServer) }
  }

  private fun getAliveServers() = explorerClient.getAll(context)

  private fun unregisterServer(registeredServer: RegisteredServer) {
    proxyServer.unregisterServer(registeredServer.serverInfo)
    logger.info("Unregistered server ${registeredServer.serverInfo.name}")
  }

  private fun registerServer(identity: Identity) {
    proxyServer.registerServer(
      ServerInfo(
        identity.id,
        InetSocketAddress.createUnresolved(identity.hostname, identity.port)
      )
    )
    logger.info("Registered server ${identity.id} on socket ${identity.hostname}:${identity.port}")
  }
}