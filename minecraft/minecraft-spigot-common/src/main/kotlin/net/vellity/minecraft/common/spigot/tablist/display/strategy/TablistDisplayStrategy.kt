package net.vellity.minecraft.common.spigot.tablist.display.strategy

import net.vellity.minecraft.common.spigot.tablist.display.TablistDisplay
import org.bukkit.entity.Player
import java.util.UUID

interface TablistDisplayStrategy {
  fun displayElement(display: TablistDisplay)

  fun removeElement(player: UUID, viewer: Player)
}