package net.vellity.minecraft.common.spigot.chat

import io.papermc.paper.event.player.AsyncChatEvent
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.globalThreadPool
import net.vellity.minecraft.common.translations.format.MiniMessageFormat
import net.vellity.minecraft.common.usercacheClient
import net.vellity.service.usercache.message.MessageSentStatus
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class ChatMessageUserCacheLogging : Listener {
  @EventHandler(priority = EventPriority.HIGH)
  private fun logMessageToUserCache(event: AsyncChatEvent) {
    globalThreadPool.submit { logMessage(event) }
  }

  private fun statusForMessage(event: AsyncChatEvent) =
    if (event.isCancelled) MessageSentStatus.CANCELLED else MessageSentStatus.SUCCESS

  private fun logMessage(event: AsyncChatEvent) {
    usercacheClient.messages().saveMessage(
      context,
      event.player.uniqueId,
      MiniMessageFormat.componentToString(event.message()),
      statusForMessage(event)
    ).execute()
  }
}