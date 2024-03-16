package net.vellity.minecraft.common.spigot.friends.menu.settings.blocked

import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.spigot.friends.menu.FriendMenu
import net.vellity.minecraft.common.usercacheClient
import net.vellity.service.friends.FriendProfile
import org.bukkit.entity.Player

class BlockedPlayersOverview(
  player: Player,
  friendProfile: FriendProfile
) : FriendMenu(player, friendProfile, "blocked") {

  init {
    settingsItem = false
    friendAddItem = false
    openRequestsItem = false
  }

  override fun drawContent() {
    setItem(4, 5, BlockPlayerItem(player, friendProfile))

    if (friendProfile.blockedPlayer.isEmpty()) {
      return
    }

    val listResponse = usercacheClient.combined()
      .bulkGetTexturesAndName(friendProfile.blockedPlayer.map { it.target })
      .execute()

    if (!listResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-friends-inventory-requests-error-load")
      return
    }

    for (data in listResponse.body()!!) {
      addItem(
        BlockedPlayerItem(
          player,
          friendProfile,
          data,
          friendProfile.blockedPlayer.first { it.target == data.uuid }
        )
      )
    }
  }
}