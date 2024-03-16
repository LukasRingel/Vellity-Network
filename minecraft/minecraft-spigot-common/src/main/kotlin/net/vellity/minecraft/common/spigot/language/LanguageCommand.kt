package net.vellity.minecraft.common.spigot.language

import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.sync
import org.bukkit.entity.Player

class LanguageCommand : PlayerCommand("language", "Change your current language") {
  override fun executeCommand(player: Player, args: Array<out String>) {
    LanguageChangeInventory(player).open()
  }
}