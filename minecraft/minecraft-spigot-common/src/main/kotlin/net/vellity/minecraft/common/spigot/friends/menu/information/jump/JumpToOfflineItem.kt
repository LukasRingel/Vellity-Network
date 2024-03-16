package net.vellity.minecraft.common.spigot.friends.menu.information.jump

import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.service.usercache.combined.TextureAndName
import org.bukkit.Material
import org.bukkit.entity.Player

class JumpToOfflineItem(player: Player, textureAndName: TextureAndName) : GuiItem(
  ItemFactory.item(Material.BARRIER)
    .name("commons-friends-inventory-information-item-jumpto-offline-name")
    .lore("commons-friends-inventory-information-item-jumpto-offline-lore")
    .replacements(
      "name",
      ColoredPlayerName.from(textureAndName.name, textureAndName.uuid, player.uniqueId)
    )
    .build(player)
)