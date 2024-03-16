package net.vellity.minecraft.common.spigot.friends.menu.information

import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.minecraft.common.translations.transformer.player.color.PrefixedPlayerName
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.service.usercache.combined.TextureAndName
import org.bukkit.entity.Player

class FriendSkullWithNameItem(player: Player, textureAndName: TextureAndName) : GuiItem(
  ItemFactory.playerHeadWithTexture(textureAndName.textureValue)
    .name("commons-friends-inventory-information-item-friend-name")
    .lore("commons-friends-inventory-information-item-friend-lore")
    .replacements(
      "name",
      ColoredPlayerName.from(textureAndName.name, textureAndName.uuid, player.uniqueId)
    )
    .build(player)
)