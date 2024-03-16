package net.vellity.minecraft.common.spigot.commands

import net.vellity.minecraft.common.permissions.NetworkPermissions
import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerAccessExtensions.hasAccess
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.spigot.extensions.PlayerTransformerExtensions.coloredName
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class PingCommand : PlayerCommand("ping", "Get your ping") {
  override fun executeCommand(player: Player, args: Array<out String>) {
    if (args.isEmpty()) {
      sendOwnPing(player)
      return
    }

    if (!player.hasAccess(NetworkPermissions.COMMAND_PING_OTHER)) {
      sendAccessErrorMessage(player)
      return
    }

    val target = Bukkit.getPlayer(args[0])
    if (target == null) {
      player.sendTranslatedMessage("commons-command-ping-error-player", "player", args[0])
      return
    }

    sendOtherPing(player, target)
  }

  private fun sendOtherPing(player: Player, target: Player) {
    player.sendTranslatedMessage(
      "commons-command-ping-other",
      "player",
      target.coloredName(),
      "ping",
      target.ping
    )
  }

  private fun sendOwnPing(player: Player) {
    player.sendTranslatedMessage("commons-command-ping-own", "ping", player.ping)
  }
}