package net.vellity.minecraft.common.spigot.friends.menu.settings

import net.vellity.minecraft.common.spigot.friends.menu.settings.blocked.BlockedPlayersOverview
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.minecraft.common.spigot.item.textures.Textures
import net.vellity.service.friends.FriendProfile
import org.bukkit.entity.Player

class BlockedPlayersItem(player: Player, friendProfile: FriendProfile) : GuiItem(
  ItemFactory.playerHeadWithTexture(Textures.PAUSE_RED)
    .name("commons-friends-inventory-settings-item-blocked-name")
    .lore("commons-friends-inventory-settings-item-blocked-lore")
    .build(player),
  { BlockedPlayersOverview(player, friendProfile).open() }
)