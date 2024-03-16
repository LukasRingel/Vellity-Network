package net.vellity.minecraft.common.spigot.friends.menu.settings

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.friendsClient
import net.vellity.minecraft.common.spigot.extensions.sync
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.minecraft.common.spigot.item.textures.Textures
import net.vellity.service.friends.FriendProfile
import net.vellity.service.friends.Setting
import net.vellity.service.friends.SettingState
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

class FriendRequestSettingToggle(
  player: Player,
  friendProfile: FriendProfile,
  previousGui: FriendSettingsMenu
) : GuiItem(
  ItemFactory.playerHeadWithTexture(textureForHead(friendProfile))
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
      val state = friendProfile.getSettingState(Setting.REQUESTS)
      return when (state) {
        SettingState.UNKNOWN -> SettingState.UNKNOWN // should not happen for requests setting
        SettingState.EVERYBODY -> SettingState.NOBODY
        SettingState.FRIENDS -> SettingState.FRIENDS // should not happen for requests setting
        SettingState.FAVORITES -> SettingState.FAVORITES // should not happen for requests setting
        SettingState.NOBODY -> SettingState.EVERYBODY
      }
    }

    private fun updateState(player: Player, friendProfile: FriendProfile) {
      val nextState = nextState(friendProfile)
      friendProfile.settings[Setting.REQUESTS] = nextState
      friendsClient.settings().updateSetting(
        context,
        player.uniqueId,
        Setting.REQUESTS,
        nextState
      ).execute()

    }

    private fun nameForSettingState(friendProfile: FriendProfile): String {
      val name = friendProfile.getSettingState(Setting.REQUESTS).name.lowercase()
      return "commons-friends-inventory-settings-item-requests-$name-name"
    }

    private fun loreForSettingState(friendProfile: FriendProfile): String {
      val name = friendProfile.getSettingState(Setting.REQUESTS).name.lowercase()
      return "commons-friends-inventory-settings-item-requests-$name-lore"
    }

    private fun textureForHead(friendProfile: FriendProfile): String {
      return when (friendProfile.getSettingState(Setting.REQUESTS)) {
        SettingState.UNKNOWN -> Textures.QUESTION_MARK_OAK // should not happen for requests setting
        SettingState.EVERYBODY -> Textures.PLUS_GREEN
        SettingState.FRIENDS -> Textures.QUESTION_MARK_OAK // should not happen for requests setting
        SettingState.FAVORITES -> Textures.QUESTION_MARK_OAK // should not happen for requests setting
        SettingState.NOBODY -> Textures.PLUS_RED
      }
    }
  }
}