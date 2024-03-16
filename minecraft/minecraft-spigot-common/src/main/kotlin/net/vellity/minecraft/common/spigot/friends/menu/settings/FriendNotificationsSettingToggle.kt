package net.vellity.minecraft.common.spigot.friends.menu.settings

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.friendsClient
import net.vellity.minecraft.common.spigot.extensions.sync
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.service.friends.FriendProfile
import net.vellity.service.friends.Setting
import net.vellity.service.friends.SettingState
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

class FriendNotificationsSettingToggle(
  player: Player,
  friendProfile: FriendProfile,
  previousGui: FriendSettingsMenu
) : GuiItem(
  ItemFactory.item(musicDiskWithColor(friendProfile))
    .name(nameForSettingState(friendProfile))
    .lore(loreForSettingState(friendProfile))
    .hideFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ITEM_SPECIFICS)
    .build(player),
  { _ ->
    updateState(player, friendProfile)
    sync { previousGui.open() }
  }
) {
  companion object {
    private fun nextState(friendProfile: FriendProfile): SettingState {
      val state = friendProfile.getSettingState(Setting.NOTIFICATIONS)
      return when (state) {
        SettingState.UNKNOWN -> SettingState.UNKNOWN // should not happen for notifications setting
        SettingState.EVERYBODY -> SettingState.FRIENDS // should not happen for notifications setting
        SettingState.FRIENDS -> SettingState.FAVORITES
        SettingState.FAVORITES -> SettingState.NOBODY
        SettingState.NOBODY -> SettingState.FRIENDS
      }
    }

    private fun updateState(player: Player, friendProfile: FriendProfile) {
      val nextState = nextState(friendProfile)
      friendProfile.settings[Setting.NOTIFICATIONS] = nextState
      friendsClient.settings().updateSetting(
        context,
        player.uniqueId,
        Setting.NOTIFICATIONS,
        nextState
      ).execute()
    }

    private fun nameForSettingState(friendProfile: FriendProfile): String {
      val name = friendProfile.getSettingState(Setting.NOTIFICATIONS).name.lowercase()
      return "commons-friends-inventory-settings-item-notifications-$name-name"
    }

    private fun loreForSettingState(friendProfile: FriendProfile): String {
      val name = friendProfile.getSettingState(Setting.NOTIFICATIONS).name.lowercase()
      return "commons-friends-inventory-settings-item-notifications-$name-lore"
    }

    private fun musicDiskWithColor(friendProfile: FriendProfile): Material {
      return when (friendProfile.getSettingState(Setting.NOTIFICATIONS)) {
        SettingState.UNKNOWN -> Material.BARRIER // should not happen for notifications setting
        SettingState.EVERYBODY -> Material.BARRIER // should not happen for notifications setting
        SettingState.FRIENDS -> Material.MUSIC_DISC_CAT
        SettingState.FAVORITES -> Material.MUSIC_DISC_13
        SettingState.NOBODY -> Material.MUSIC_DISC_BLOCKS
      }
    }
  }
}