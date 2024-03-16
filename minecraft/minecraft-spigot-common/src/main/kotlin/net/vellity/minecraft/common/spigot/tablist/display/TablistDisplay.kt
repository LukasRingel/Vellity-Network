package net.vellity.minecraft.common.spigot.tablist.display

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import java.util.UUID

data class TablistDisplay(
  val target: UUID,
  val viewer: Player,
  val priority: Int,
  val prefix: Component,
  val name: Component,
  val suffix: Component,
)