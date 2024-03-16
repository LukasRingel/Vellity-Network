package net.vellity.minecraft.common.spigot.chat.synchronizer

import org.bukkit.entity.Player

interface ChatSynchronizer {
  fun publishMessage(sender: Player, message: String)

  fun receiveMessages()
}