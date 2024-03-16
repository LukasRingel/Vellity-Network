package net.vellity.minecraft.common.spigot.tablist.display.dataprovider

import net.vellity.minecraft.common.spigot.tablist.display.TablistDisplay
import org.bukkit.entity.Player
import java.util.*

interface TablistDataProvider {
  fun createDisplayFor(targetUuid: UUID, targetName: String, viewer: Player): TablistDisplay
}