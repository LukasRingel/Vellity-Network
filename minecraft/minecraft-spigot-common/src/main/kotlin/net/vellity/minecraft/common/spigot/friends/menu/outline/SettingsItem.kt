package net.vellity.minecraft.common.spigot.friends.menu.outline

import net.vellity.minecraft.common.spigot.friends.menu.settings.FriendSettingsMenu
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.service.friends.FriendProfile
import org.bukkit.Material
import org.bukkit.entity.Player

class SettingsItem(player: Player, friendProfile: FriendProfile) : GuiItem(
  ItemFactory.item(Material.COMPARATOR)
    .name("commons-friends-inventory-outline-item-settings-name")
    .lore("commons-friends-inventory-outline-item-settings-lore")
    .build(player),
  { FriendSettingsMenu(player, friendProfile).open() }
)