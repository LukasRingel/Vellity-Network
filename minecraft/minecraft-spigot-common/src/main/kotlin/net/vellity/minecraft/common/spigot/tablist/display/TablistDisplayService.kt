package net.vellity.minecraft.common.spigot.tablist.display

import net.vellity.minecraft.common.spigot.tablist.display.dataprovider.FallbackDataProvider
import net.vellity.minecraft.common.spigot.tablist.display.dataprovider.TablistDataProvider
import net.vellity.minecraft.common.spigot.tablist.display.strategy.ScoreboardDisplayStrategy
import net.vellity.minecraft.common.spigot.tablist.display.strategy.TablistDisplayStrategy
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

object TablistDisplayService : Listener {
  private var dataProvider: TablistDataProvider = FallbackDataProvider()
  private var displayStrategy: TablistDisplayStrategy = ScoreboardDisplayStrategy()

  private fun rerenderTablist() {
    for (player in Bukkit.getOnlinePlayers()) {
      renderTablist(player)
    }
  }

  fun renderTablist(player: Player) {
    for (viewer in Bukkit.getOnlinePlayers()) {
      displayStrategy.displayElement(
        dataProvider.createDisplayFor(player.uniqueId, player.name, viewer)
      )
      displayStrategy.displayElement(
        dataProvider.createDisplayFor(viewer.uniqueId, viewer.name, player)
      )
    }
  }

  fun removeTablistElement(player: UUID) {
    for (viewer in Bukkit.getOnlinePlayers()) {
      displayStrategy.removeElement(player, viewer)
    }
  }

  fun updateDataProviderAndRerenderTablist(dataProvider: TablistDataProvider) {
    this.dataProvider = dataProvider
    rerenderTablist()
  }

  @EventHandler
  private fun renderTablistOnJoin(event: PlayerJoinEvent) {
    renderTablist(event.player)
  }

  @EventHandler
  private fun removeTablistElementForOthersOnLeave(event: PlayerQuitEvent) {
    removeTablistElement(event.player.uniqueId)
  }
}