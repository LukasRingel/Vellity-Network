package net.vellity.minecraft.common.spigot.friends.menu.outline

import net.vellity.minecraft.common.spigot.friends.menu.overview.FriendOverviewMenu
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.minecraft.common.spigot.item.textures.Textures
import net.vellity.service.friends.FriendProfile
import org.bukkit.entity.Player

class BackToMainMenu(player: Player, friendProfile: FriendProfile): GuiItem(
  ItemFactory.playerHeadWithTexture(Textures.ARROW_BACK_RED)
    .name("commons-friends-inventory-outline-item-back-name")
    .lore("commons-friends-inventory-outline-item-back-lore")
    .build(player),
  { FriendOverviewMenu(player, friendProfile).open()}
)