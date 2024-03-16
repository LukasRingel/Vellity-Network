package net.vellity.minecraft.common.spigot.commands

import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import org.bukkit.entity.Player

class HelpCommand: PlayerCommand("help", "Shows a pretty help message") {
  override fun executeCommand(player: Player, args: Array<out String>) {
    player.sendTranslatedMessage("commons-command-help")
  }
}