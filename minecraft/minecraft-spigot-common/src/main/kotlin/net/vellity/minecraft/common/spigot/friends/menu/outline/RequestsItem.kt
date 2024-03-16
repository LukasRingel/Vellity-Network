package net.vellity.minecraft.common.spigot.friends.menu.outline

import net.vellity.minecraft.common.spigot.friends.menu.requests.FriendRequestsMenu
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.minecraft.common.spigot.item.textures.Textures
import net.vellity.service.friends.FriendProfile
import org.bukkit.entity.Player

class RequestsItem(player: Player, friendProfile: FriendProfile) : GuiItem(
  ItemFactory.playerHeadWithTexture(Textures.QUESTION_MARK_OAK)
    .name("commons-friends-inventory-outline-item-requests-name")
    .lore("commons-friends-inventory-outline-item-requests-lore")
    .build(player),
  { FriendRequestsMenu(player, friendProfile).open() }
)