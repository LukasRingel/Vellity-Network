package net.vellity.minecraft.common.spigot.tablist

import net.kyori.adventure.text.Component
import net.vellity.minecraft.common.globalThreadPool
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.translate
import net.vellity.minecraft.common.spigot.language.LanguageChangeEvent
import net.vellity.minecraft.common.translations.transformer.server.ServerGroupDisplay
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.util.concurrent.Executors

object HeaderFooterService : Listener {
  var header: (Player) -> Component =
    { player -> player.translate("commons-tablist-header", "serverGroup", ServerGroupDisplay()) }
  var footer: (Player) -> Component = { player -> player.translate("commons-tablist-footer") }

  init {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
      for (player in Bukkit.getOnlinePlayers()) {
        player.sendPlayerListHeaderAndFooter(
          header(player),
          footer(player)
        )
      }
    }, 0, 5, java.util.concurrent.TimeUnit.SECONDS)
  }

  @EventHandler
  private fun applyHeaderFooterOnJoin(event: PlayerJoinEvent) {
    globalThreadPool.submit {
      event.player.sendPlayerListHeaderAndFooter(
        header(event.player),
        footer(event.player)
      )
    }
  }

  @EventHandler
  private fun applyHeaderFooterOnLanguageChange(event: LanguageChangeEvent) {
    globalThreadPool.submit {
      event.player.sendPlayerListHeaderAndFooter(
        header(event.player),
        footer(event.player)
      )
    }
  }
}