package net.vellity.minecraft.common.spigot.friends.menu.information.favorite

import net.vellity.minecraft.common.spigot.extensions.sync
import net.vellity.minecraft.common.spigot.friends.action.FavoriteToggleAction
import net.vellity.minecraft.common.spigot.friends.menu.information.FriendInformationMenu
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.minecraft.common.spigot.item.textures.Textures
import net.vellity.service.friends.Friendship
import net.vellity.service.friends.FriendshipState
import org.bukkit.entity.Player

class FriendDemoteToNormalItem(
  player: Player,
  friendship: Friendship,
  previousGui: FriendInformationMenu
) : GuiItem(
  ItemFactory.playerHeadWithTexture(Textures.ARROW_DOWN_GOLD)
    .name("commons-friends-inventory-information-item-demote-name")
    .lore("commons-friends-inventory-information-item-demote-lore")
    .build(player),
  { _ ->
    FavoriteToggleAction(player, friendship, FriendshipState.NORMAL).run()
    friendship.state = FriendshipState.NORMAL
    sync { previousGui.open() }
  }
)