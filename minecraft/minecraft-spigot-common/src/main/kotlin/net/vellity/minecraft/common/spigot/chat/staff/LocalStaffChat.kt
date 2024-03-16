package net.vellity.minecraft.common.spigot.chat.staff

import net.vellity.minecraft.common.permissions.NetworkPermissions
import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerAccessExtensions.hasAccess
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.spigot.extensions.PlayerUserCacheExtensions.hasNotificationEnabled
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.service.usercache.notification.Notification
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class LocalStaffChat : PlayerCommand(
  "sl",
  "Send a message to local staff members",
  NetworkPermissions.CHANNEL_STAFF_LOCAL
) {
  override fun executeCommand(player: Player, args: Array<out String>) {
    if (args.isEmpty()) {
      player.sendTranslatedMessage("commons-chat-staff-local-empty")
      return
    }

    if (!player.hasNotificationEnabled(Notification.GLOBAL_CHAT_STAFF)) {
      player.sendTranslatedMessage("commons-chat-staff-local-disabled")
      return
    }

    val coloredName = ColoredPlayerName.from(player.name, player.uniqueId).getColoredName()

    Bukkit.getOnlinePlayers()
      .filter { it.hasAccess(NetworkPermissions.CHANNEL_STAFF_LOCAL) }
      .filter { it.hasNotificationEnabled(Notification.GLOBAL_CHAT_STAFF) }
      .forEach {
        it.sendTranslatedMessage(
          "commons-chat-staff-local",
          "sender",
          coloredName,
          "message",
          args.joinToString(" ")
        )
      }
  }
}