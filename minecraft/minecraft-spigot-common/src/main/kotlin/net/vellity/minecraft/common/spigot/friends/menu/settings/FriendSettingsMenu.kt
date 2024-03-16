package net.vellity.minecraft.common.spigot.friends.menu.settings

import net.vellity.minecraft.common.spigot.friends.menu.FriendMenu
import net.vellity.service.friends.FriendProfile
import org.bukkit.entity.Player

class FriendSettingsMenu(
  player: Player,
  friendProfile: FriendProfile
) : FriendMenu(player, friendProfile, "settings") {
  override fun drawContent() {
    setItem(2, 2, FriendJumpSettingToggle(player, friendProfile, this))
    setItem(4, 2, FriendRequestSettingToggle(player, friendProfile, this))
    setItem(6, 2, PrivateMessageSettingToggle(player, friendProfile, this))

    setItem(3, 3, FriendNotificationsSettingToggle(player, friendProfile, this))
    setItem(5, 3, BlockedPlayersItem(player, friendProfile))
  }
}