package net.vellity.minecraft.common.spigot.extensions

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

object PlayerDisconnectHookExtensions : Listener {
  private val hooks = mutableMapOf<UUID, MutableMap<String, () -> Unit>>()

  fun Player.registerDisconnectHook(key: String, hook: () -> Unit) {
    hooks[this.uniqueId] = hooks.getOrDefault(this.uniqueId, mutableMapOf()).apply { this[key] = hook }
  }

  fun Player.removeDisconnectHook(key: String) {
    hooks[this.uniqueId]?.remove(key)
  }

  @EventHandler
  private fun runHooksOnDisconnect(event: PlayerQuitEvent) {
    hooks[event.player.uniqueId]?.forEach { it.value() }
    hooks.remove(event.player.uniqueId)
  }
}