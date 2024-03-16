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

class PrivateMessageSettingToggle(
  player: Player,
  friendProfile: FriendProfile,
  previousGui: FriendSettingsMenu
) : GuiItem(
  ItemFactory.item(signWithColor(friendProfile))
    .name(nameForSettingState(friendProfile))
    .lore(loreForSettingState(friendProfile))
    .hideFlags(ItemFlag.HIDE_ATTRIBUTES)
    .build(player),
  { _ ->
    updateState(player, friendProfile)
    sync { previousGui.open() }
  }
) {
  companion object {
    private fun nextState(friendProfile: FriendProfile): SettingState {
      val state = friendProfile.getSettingState(Setting.PRIVATE_MESSAGES)
      return when (state) {
        SettingState.UNKNOWN -> SettingState.UNKNOWN // should not happen for private message setting
        SettingState.EVERYBODY -> SettingState.FRIENDS
        SettingState.FRIENDS -> SettingState.FAVORITES
        SettingState.FAVORITES -> SettingState.NOBODY
        SettingState.NOBODY -> SettingState.EVERYBODY
      }
    }

    private fun updateState(player: Player, friendProfile: FriendProfile) {
      val nextState = nextState(friendProfile)
      friendProfile.settings[Setting.PRIVATE_MESSAGES] = nextState
      friendsClient.settings().updateSetting(
        context,
        player.uniqueId,
        Setting.PRIVATE_MESSAGES,
        nextState
      ).execute()

    }

    private fun nameForSettingState(friendProfile: FriendProfile): String {
      val name = friendProfile.getSettingState(Setting.PRIVATE_MESSAGES).name.lowercase()
      return "commons-friends-inventory-settings-item-privatemessage-$name-name"
    }

    private fun loreForSettingState(friendProfile: FriendProfile): String {
      val name = friendProfile.getSettingState(Setting.PRIVATE_MESSAGES).name.lowercase()
      return "commons-friends-inventory-settings-item-privatemessage-$name-lore"
    }

    private fun signWithColor(friendProfile: FriendProfile): Material {
      return when (friendProfile.getSettingState(Setting.PRIVATE_MESSAGES)) {
        SettingState.UNKNOWN -> Material.BARRIER // should not happen for private message setting
        SettingState.EVERYBODY -> Material.BAMBOO_HANGING_SIGN
        SettingState.FRIENDS -> Material.WARPED_HANGING_SIGN
        SettingState.FAVORITES -> Material.ACACIA_HANGING_SIGN
        SettingState.NOBODY -> Material.CRIMSON_HANGING_SIGN
      }
    }
  }
}