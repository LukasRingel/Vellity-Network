package net.vellity.minecraft.common.spigot.chat

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import net.vellity.minecraft.common.spigot.chat.format.ChatMessageBroadcaster
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class LocalChatFormat : Listener {
  @EventHandler(priority = EventPriority.HIGHEST)
  private fun formatMessage(event: AsyncChatEvent) {
    if(event.isCancelled) {
      return
    }
    event.viewers().clear()
    ChatMessageBroadcaster.broadcastMessage(
      event.player.uniqueId,
      event.player.name,
      (event.message() as TextComponent).content()
    )
  }
}