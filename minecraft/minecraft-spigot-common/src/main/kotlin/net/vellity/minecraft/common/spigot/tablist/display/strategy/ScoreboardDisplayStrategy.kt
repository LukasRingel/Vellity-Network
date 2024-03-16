package net.vellity.minecraft.common.spigot.tablist.display.strategy

import net.vellity.minecraft.common.spigot.tablist.display.TablistDisplay
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

class ScoreboardDisplayStrategy : TablistDisplayStrategy {
  override fun displayElement(display: TablistDisplay) {
    var scoreboard = display.viewer.scoreboard

    if (scoreboard == null) {
      scoreboard = Bukkit.getScoreboardManager().newScoreboard
    }

    val teamName = generateTeamName(display)
    val team = if (scoreboard.getTeam(teamName) == null)
      scoreboard.registerNewTeam(teamName) else scoreboard.getTeam(teamName)!!

    team.prefix(display.prefix)
    team.suffix(display.suffix)
  }

  override fun removeElement(player: UUID, viewer: Player) {
  }

  private fun generateTeamName(display: TablistDisplay): String {
    return "${display.priority}-${display.name}"
  }
}