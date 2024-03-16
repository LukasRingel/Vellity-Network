package net.vellity.minecraft.common.spigot.tablist.display.dataprovider

import net.kyori.adventure.text.Component
import net.vellity.minecraft.common.spigot.tablist.display.TablistDisplay
import net.vellity.minecraft.common.translations.format.MiniMessageFormat
import org.bukkit.entity.Player
import java.util.*

class FallbackDataProvider : TablistDataProvider {
  override fun createDisplayFor(targetUuid: UUID, targetName: String, viewer: Player): TablistDisplay =
    TablistDisplay(
      targetUuid,
      viewer,
      0,
      Component.empty(),
      MiniMessageFormat.format("<neural>$targetName</neutral>"),
      Component.empty()
    )
}