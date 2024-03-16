package net.vellity.minecraft.common.spigot.chat.format

import org.bukkit.Bukkit
import java.util.*

object ChatMessageBroadcaster {
  var messageFormat: MessageFormat = ColoredDisplayNameFormat()

  fun broadcastMessage(senderUuid: UUID, senderName: String, message: String) {
    for (onlinePlayer in Bukkit.getOnlinePlayers()) {
      onlinePlayer.sendMessage(
        messageFormat.format(
          senderUuid,
          senderName,
          message,
          onlinePlayer
        )
      )
    }
  }
}