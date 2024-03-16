package net.vellity.minecraft.common.spigot.chat.staff

import net.vellity.minecraft.common.permissions.NetworkPermissions
import net.vellity.minecraft.common.spigot.chat.synchronizer.ChatSynchronizerService
import net.vellity.minecraft.common.spigot.chat.synchronizer.RedisChatSynchronizer
import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerAccessExtensions.hasAccess
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.spigot.extensions.PlayerUserCacheExtensions.hasNotificationEnabled
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.service.usercache.notification.Notification
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class GlobalStaffChat : PlayerCommand(
  "sg",
  "Send a message to all staff members",
  NetworkPermissions.CHANNEL_STAFF_GLOBAL
) {
  init {
    ChatSynchronizerService.registerSynchronizer(CHAT_NAME, RedisChatSynchronizer(CHAT_NAME) {
      sendMessageToLocalMembers(it.sender, it.originalName, it.message)
    })
  }

  override fun executeCommand(player: Player, args: Array<out String>) {
    if (args.isEmpty()) {
      player.sendTranslatedMessage("commons-chat-staff-global-empty")
      return
    }

    if (!player.hasNotificationEnabled(Notification.GLOBAL_CHAT_STAFF)) {
      player.sendTranslatedMessage("commons-chat-staff-global-disabled")
      return
    }

    ChatSynchronizerService.publishMessage(CHAT_NAME, player, args.joinToString(" "))
  }

  private fun sendMessageToLocalMembers(sender: UUID, name: String, message: String) {
    val coloredName = ColoredPlayerName.from(name, sender).getColoredName()
    Bukkit.getOnlinePlayers()
      .filter { it.hasAccess(NetworkPermissions.CHANNEL_STAFF_GLOBAL) }
      .filter { it.hasNotificationEnabled(Notification.GLOBAL_CHAT_STAFF) }
      .forEach {
        it.sendTranslatedMessage(
          "commons-chat-staff-global",
          "sender",
          coloredName,
          "message",
          message
        )
      }
  }

  companion object {
    private const val CHAT_NAME = "globalStaffChat"
  }
}