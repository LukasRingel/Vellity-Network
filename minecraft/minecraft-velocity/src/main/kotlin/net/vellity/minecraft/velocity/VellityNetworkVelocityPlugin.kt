package net.vellity.minecraft.velocity

import com.google.inject.Inject
import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import net.vellity.minecraft.common.*
import net.vellity.minecraft.common.player.ServerChangeToExactIdRequest
import net.vellity.minecraft.velocity.communcation.SpigotCommunicationService
import net.vellity.minecraft.velocity.login.events.VelocityLoginEventListener
import net.vellity.minecraft.velocity.login.maintenance.DirectMaintenanceLoginCheck
import net.vellity.minecraft.velocity.login.punish.DirectPunishmentLoginCheck
import net.vellity.minecraft.velocity.login.usercache.UserCacheUpdateListener
import net.vellity.minecraft.velocity.login.usercache.UserSessionKeepAliveTask
import net.vellity.minecraft.velocity.motd.MotDService
import net.vellity.minecraft.velocity.motd.MotdDisplayListener
import net.vellity.minecraft.velocity.server.AliveServerService
import net.vellity.minecraft.velocity.server.InitialServerService
import net.vellity.minecraft.velocity.server.ServerChangeToExactIdListener
import net.vellity.minecraft.velocity.server.ServerChooseListener
import net.vellity.utils.configuration.Environment
import org.slf4j.Logger

@Plugin(
  id = "vellity-network-velocity",
  name = "vellity-network-velocity",
  version = "1.0-SNAPSHOT",
  description = "Connects Velocity to the Vellity Network and its services",
  authors = ["lvkaas"],
  url = "https://api.vellity.net"
)
class VellityNetworkVelocityPlugin @Inject constructor(
  private val proxyServer: ProxyServer,
  private val logger: Logger
) {

  @Subscribe
  private fun initialize(event: ProxyInitializeEvent) {
    logger.info("Hello, Velocity!")
    registerAndAcquireIdentity()
    loadAndStartCommonServices()
    registerListeners(proxyServer.eventManager)
  }

  @Subscribe
  private fun shutdown(event: ProxyShutdownEvent) {
    explorerClient.unregister(serverGroupName, identity.id, context)
  }

  private fun registerAndAcquireIdentity() {
    // we execute this blocking to ensure that
    // other plugins always know their identify
    identity = explorerClient.register(
      serverGroupName,
      proxyServer.boundAddress.port,
      Environment.getOrDefault("SERVER_ADDRESS", "localhost"),
      context
    )
    logger.info("Identity acquired: We are ${identity.id}")
    ExplorerRegistrationWorker.create(
      Environment.getOrDefault("SERVER_ADDRESS", "localhost"),
      proxyServer.boundAddress.port
    )
  }

  private fun loadAndStartCommonServices() {
    CommonsStartup.load()
    CommonsStartup.start()
    MotDService.startUpdateTask()
    InitialServerService.startUpdateTask()
    UserSessionKeepAliveTask(proxyServer).startUpdateTask()
    AliveServerService(proxyServer, logger).startAutomaticServerRegistration()
    SpigotCommunicationService.registerListener(
      ServerChangeToExactIdRequest.TOPIC,
      ServerChangeToExactIdListener(proxyServer)
    )
  }

  private fun registerListeners(eventManager: EventManager) {
    eventManager.register(
      this, VelocityLoginEventListener(
        DirectMaintenanceLoginCheck(),
        DirectPunishmentLoginCheck(),
        eventManager
      )
    )
    eventManager.register(this, MotdDisplayListener())
    eventManager.register(this, UserCacheUpdateListener())
    eventManager.register(this, ServerChooseListener(proxyServer))
  }
}