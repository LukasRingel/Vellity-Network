package net.vellity.minecraft.common.spigot.session

import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.spigot.extensions.PlayerUserCacheExtensions.session
import org.bukkit.entity.Player

class WhereAmICommand : PlayerCommand("whereami", "Shows your current session", "", mutableListOf("wai")) {
  override fun executeCommand(player: Player, args: Array<out String>) {
    player.session().let { session ->

      if (session.login == 0) {
        player.sendTranslatedMessage("commons-command-whereami-error")
        return
      }

      player.sendTranslatedMessage(
        "commons-command-whereami-success",
        "proxy",
        session.currentProxy,
        "current",
        session.currentServer,
        "previous",
        session.previousServer
      )
    }
  }
}