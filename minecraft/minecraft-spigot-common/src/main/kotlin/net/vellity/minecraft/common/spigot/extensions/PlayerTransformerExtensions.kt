package net.vellity.minecraft.common.spigot.extensions

import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import org.bukkit.entity.Player

object PlayerTransformerExtensions {
  fun Player.coloredName(): ColoredPlayerName {
    return ColoredPlayerName.from(this.uniqueId)
  }
}