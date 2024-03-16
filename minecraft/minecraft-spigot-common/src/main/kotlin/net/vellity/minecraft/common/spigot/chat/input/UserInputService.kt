package net.vellity.minecraft.common.spigot.chat.input

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.TextComponent
import net.vellity.minecraft.common.spigot.extensions.PlayerDisconnectHookExtensions.registerDisconnectHook
import net.vellity.minecraft.common.spigot.extensions.PlayerDisconnectHookExtensions.removeDisconnectHook
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

object UserInputService : Listener {
  private const val DISCONNECT_HOOK_KEY = "UserInputService"
  private val inputs = mutableListOf<UserInput>()

  fun awaitInput(player: Player, callback: (String) -> Unit) {
    inputs.add(UserInput(player.uniqueId, callback))
    player.registerDisconnectHook(DISCONNECT_HOOK_KEY) {
      inputs.removeIf { it.player == player.uniqueId }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  private fun onChat(event: AsyncChatEvent) {
    if (event.originalMessage() !is TextComponent) {
      return
    }

    val input = inputs.firstOrNull { it.player == event.player.uniqueId } ?: return
    val textComponent = event.originalMessage() as TextComponent

    event.isCancelled = true
    input.callback(textComponent.content())
    inputs.removeIf { it.player == event.player.uniqueId }
    event.player.removeDisconnectHook(DISCONNECT_HOOK_KEY)
  }
}