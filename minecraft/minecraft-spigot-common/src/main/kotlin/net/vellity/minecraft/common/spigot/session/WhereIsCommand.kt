package net.vellity.minecraft.common.spigot.session

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.permissions.NetworkPermissions
import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.minecraft.common.usercacheClient
import org.bukkit.entity.Player

class WhereIsCommand : PlayerCommand(
  "whereis",
  "Shows the current session of a player",
  NetworkPermissions.COMMAND_WHERE_IS) {
  override fun executeCommand(player: Player, args: Array<out String>) {
    val uuidResponse = usercacheClient.names().getUuidByName(args[0]).execute()

    if (!uuidResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-command-whereis-error-name")
      return
    }

    val sessionResponse = usercacheClient.sessions().getCurrentSession(context, uuidResponse.body()!!).execute()

    if (!sessionResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-command-whereis-error-session")
      return
    }

    val session = sessionResponse.body()!!
    player.sendTranslatedMessage(
      "commons-command-whereis-success",
      "player",
      ColoredPlayerName.from(args[0], uuidResponse.body()!!),
      "proxy",
      session.currentProxy,
      "current",
      session.currentServer,
      "previous",
      session.previousServer
    )
  }
}