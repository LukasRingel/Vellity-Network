package net.vellity.minecraft.common.spigot.chat.synchronizer

import org.bukkit.entity.Player

object ChatSynchronizerService {
  private val synchronize = mutableMapOf<String, ChatSynchronizer>()

  fun registerSynchronizer(name: String, synchronizer: ChatSynchronizer) {
    synchronize[name] = synchronizer
    synchronizer.receiveMessages()
  }

  fun publishMessage(name: String, sender: Player, message: String) {
    synchronize[name]?.publishMessage(sender, message)
  }
}