package net.vellity.minecraft.common.spigot.language

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.*

class LanguageChangeEvent(val player: Player, val languageBefore: Locale, val languageAfter: Locale) : Event(true) {
  override fun getHandlers(): HandlerList {
    return HANDLERS
  }

  companion object {
    private val HANDLERS = HandlerList()

    @JvmStatic
    fun getHandlerList(): HandlerList {
      return HANDLERS
    }
  }
}