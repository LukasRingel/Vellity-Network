package net.vellity.minecraft.common.spigot

import net.vellity.minecraft.common.*
import net.vellity.minecraft.common.spigot.punish.CancelChatMessageIfPunished
import net.vellity.minecraft.common.spigot.chat.ChatMessageUserCacheLogging
import net.vellity.minecraft.common.spigot.chat.LocalChatFormat
import net.vellity.minecraft.common.spigot.chat.ServerGroupChatSynchronizer
import net.vellity.minecraft.common.spigot.chat.broadcast.BroadcastService
import net.vellity.minecraft.common.spigot.chat.broadcast.advertisement.AdvertisementBroadcastService
import net.vellity.minecraft.common.spigot.chat.input.UserInputService
import net.vellity.minecraft.common.spigot.chat.staff.GlobalStaffChat
import net.vellity.minecraft.common.spigot.chat.staff.LocalStaffChat
import net.vellity.minecraft.common.spigot.commands.*
import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.commands.economy.EconomyCommand
import net.vellity.minecraft.common.spigot.commands.economy.EconomyManagementCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerDisconnectHookExtensions
import net.vellity.minecraft.common.spigot.friends.commands.FriendCommand
import net.vellity.minecraft.common.spigot.friends.commands.MsgCommand
import net.vellity.minecraft.common.spigot.gui.GuiClickExecutor
import net.vellity.minecraft.common.spigot.gui.GuiPluginHolder
import net.vellity.minecraft.common.spigot.language.LanguageCommand
import net.vellity.minecraft.common.spigot.punish.PunishCommand
import net.vellity.minecraft.common.spigot.session.UpdateCurrentServer
import net.vellity.minecraft.common.spigot.session.WhereAmICommand
import net.vellity.minecraft.common.spigot.session.WhereIsCommand
import net.vellity.minecraft.common.spigot.tablist.HeaderFooterService
import net.vellity.minecraft.common.spigot.tablist.display.TablistDisplayService
import net.vellity.utils.configuration.Environment
import org.bukkit.Bukkit
import org.bukkit.command.SimpleCommandMap
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class VellityNetworkSpigotPlugin : JavaPlugin() {
  override fun onLoad() {
    logger.info("Hello, Spigot!")
    registerAndAcquireIdentity()
    CommonsStartup.load()
    GuiPluginHolder.setPlugin(this)
    GuiClickExecutor.setExecutor(globalThreadPool)
  }

  override fun onEnable() {
    CommonsStartup.start()
    registerCommonListeners()
    registerCommonCommands()
    initializeServices()
  }

  override fun onDisable() {
    explorerClient.unregister(serverGroupName, identity.id, context)
  }

  private fun registerAndAcquireIdentity() {
    // we execute this blocking to ensure that
    // other plugins always know their identify
    identity = explorerClient.register(
      serverGroupName,
      Bukkit.getPort(),
      Environment.getOrDefault("SERVER_ADDRESS", "localhost"),
      context
    )
    logger.info("Identity acquired: We are ${identity.id}")
    ExplorerRegistrationWorker.create(
      Environment.getOrDefault("SERVER_ADDRESS", "localhost"),
      Bukkit.getPort()
    )
  }

  private fun registerCommonCommands() {
    registerCommands(
      name,
      WhereAmICommand(),
      WhereIsCommand(),
      PingCommand(),
      HelpCommand(),
      UpdateLocalesCommand(),
      EconomyManagementCommand(),
      ChatlogCommand(),
      LanguageCommand(),
      GlobalStaffChat(),
      LocalStaffChat(),
      FriendCommand(),
      NotificationsCommand(),
      GroupCommand(),
      MsgCommand(),
      PunishCommand()
    )
    registerCommands(name, *EconomyCommand.buildEconomyCommands())
  }

  private fun registerCommonListeners() {
    registerListener(
      this,
      UpdateCurrentServer(),
      ChatMessageUserCacheLogging(),
      CancelChatMessageIfPunished(),
      HeaderFooterService,
      PlayerDisconnectHookExtensions,
      TablistDisplayService,
      UserInputService
    )

    if (Environment.getAsBooleanOrDefault("SERVER_GROUP_CHAT", true)) {
      Bukkit.getPluginManager().registerEvents(ServerGroupChatSynchronizer(), this)
    } else {
      Bukkit.getPluginManager().registerEvents(LocalChatFormat(), this)
    }
  }

  private fun initializeServices() {
    AdvertisementBroadcastService.runIfActivated()
    BroadcastService.startListening()
  }

  companion object {
    fun registerListener(plugin: Plugin, vararg listeners: Listener) {
      for (listener in listeners) {
        Bukkit.getPluginManager().registerEvents(listener, plugin)
      }
    }

    fun registerCommands(plugin: String, vararg commands: PlayerCommand) {
      val field = Bukkit.getPluginManager()::class.java.getDeclaredField("commandMap")
      field.setAccessible(true)
      val commandMap = field[Bukkit.getPluginManager()] as SimpleCommandMap
      for (command in commands) {
        commandMap.register(command.name, plugin, command)
      }
    }
  }
}