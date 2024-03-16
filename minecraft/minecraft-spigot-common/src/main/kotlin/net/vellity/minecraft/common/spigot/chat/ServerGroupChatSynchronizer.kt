package net.vellity.minecraft.common.spigot.chat

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import net.vellity.minecraft.common.identity
import net.vellity.minecraft.common.spigot.chat.format.ChatMessageBroadcaster
import net.vellity.minecraft.common.spigot.chat.synchronizer.ChatSynchronizerService
import net.vellity.minecraft.common.spigot.chat.synchronizer.RedisChatSynchronizer
import net.vellity.minecraft.common.spigot.chat.synchronizer.SynchronizedMessage
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class ServerGroupChatSynchronizer : Listener {
  init {
    ChatSynchronizerService.registerSynchronizer(CHAT_NAME, RedisChatSynchronizer(CHAT_NAME) {
      handleChatMessage(it)
    })
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  private fun broadcastMessage(event: AsyncChatEvent) {
    if (event.isCancelled) {
      return
    }

    event.viewers().clear()

    ChatSynchronizerService.publishMessage(
      CHAT_NAME,
      event.player,
      (event.message() as TextComponent).content()
    )

    ChatMessageBroadcaster.broadcastMessage(
      event.player.uniqueId,
      event.player.name,
      (event.message() as TextComponent).content()
    )
  }

  private fun handleChatMessage(message: SynchronizedMessage) {
    if (message.sourceServer == identity.id) {
      return
    }
    ChatMessageBroadcaster.broadcastMessage(
      message.sender,
      message.originalName,
      message.message
    )
  }

  companion object {
    private const val CHAT_NAME = "serverGroupChat"
  }
}