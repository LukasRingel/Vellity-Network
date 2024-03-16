package net.vellity.minecraft.common.spigot.friends.menu.information

import net.vellity.minecraft.common.spigot.extensions.sync
import net.vellity.minecraft.common.spigot.friends.action.FriendDeleteAction
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.service.friends.Friendship
import net.vellity.service.usercache.combined.TextureAndName
import org.bukkit.Material
import org.bukkit.entity.Player

class FriendDeleteItem(player: Player, friendship: Friendship, textureAndName: TextureAndName) : GuiItem(
  ItemFactory.item(Material.LAVA_BUCKET)
    .name("commons-friends-inventory-information-item-delete-name")
    .lore("commons-friends-inventory-information-item-delete-lore")
    .build(player),
  {
    FriendDeleteAction(friendship, player, textureAndName).run()
    sync { player.closeInventory() }
  }
)