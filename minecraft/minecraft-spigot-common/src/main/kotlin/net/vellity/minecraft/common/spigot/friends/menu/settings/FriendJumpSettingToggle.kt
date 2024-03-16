package net.vellity.minecraft.common.spigot.friends.menu.settings

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.friendsClient
import net.vellity.minecraft.common.spigot.extensions.sync
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.service.friends.FriendProfile
import net.vellity.service.friends.Setting
import net.vellity.service.friends.SettingState
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

class FriendJumpSettingToggle(
  player: Player, friendProfile: FriendProfile,
  previousGui: FriendSettingsMenu
) : GuiItem(
  ItemFactory.item(Material.LEATHER_BOOTS)
    .leatherColor(colorForSettingState(friendProfile))
    .name(nameForSettingState(friendProfile))
    .lore(loreForSettingState(friendProfile))
    .hideFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE)
    .build(player),
  { _ ->
    updateState(player, friendProfile)
    sync { previousGui.open() }
  }
) {
  companion object {
    private fun nextState(friendProfile: FriendProfile): SettingState {
      val state = friendProfile.getSettingState(Setting.FRIENDS_CAN_JUMP)
      return when (state) {
        SettingState.UNKNOWN -> SettingState.UNKNOWN // should not happen for jump setting
        SettingState.EVERYBODY -> SettingState.EVERYBODY // should not happen for jump setting
        SettingState.FRIENDS -> SettingState.FAVORITES
        SettingState.FAVORITES -> SettingState.NOBODY
        SettingState.NOBODY -> SettingState.FRIENDS
      }
    }

    private fun updateState(player: Player, friendProfile: FriendProfile) {
      val nextState = nextState(friendProfile)
      friendProfile.settings[Setting.FRIENDS_CAN_JUMP] = nextState
      friendsClient.settings().updateSetting(
        context,
        player.uniqueId,
        Setting.FRIENDS_CAN_JUMP,
        nextState
      ).execute()

    }

    private fun nameForSettingState(friendProfile: FriendProfile): String {
      val name = friendProfile.getSettingState(Setting.FRIENDS_CAN_JUMP).name.lowercase()
      return "commons-friends-inventory-settings-item-jump-$name-name"
    }

    private fun loreForSettingState(friendProfile: FriendProfile): String {
      val name = friendProfile.getSettingState(Setting.FRIENDS_CAN_JUMP).name.lowercase()
      return "commons-friends-inventory-settings-item-jump-$name-lore"
    }

    private fun colorForSettingState(friendProfile: FriendProfile): Color {
      return when (friendProfile.getSettingState(Setting.FRIENDS_CAN_JUMP)) {
        SettingState.UNKNOWN -> Color.GRAY
        SettingState.EVERYBODY -> Color.LIME
        SettingState.FRIENDS -> Color.GREEN
        SettingState.FAVORITES -> Color.ORANGE
        SettingState.NOBODY -> Color.RED
      }
    }
  }
}