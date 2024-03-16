package net.vellity.minecraft.common.spigot.friends.menu.requests

import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.spigot.friends.menu.FriendMenu
import net.vellity.minecraft.common.usercacheClient
import net.vellity.service.friends.FriendProfile
import org.bukkit.entity.Player

class FriendRequestsMenu(
  player: Player,
  friendProfile: FriendProfile
) : FriendMenu(player, friendProfile, "requests") {
  override fun drawContent() {
    if (friendProfile.receivedRequests.isEmpty()) {
      return
    }

    val listResponse = usercacheClient.combined()
      .bulkGetTexturesAndName(friendProfile.receivedRequests.map { it.sender })
      .execute()

    if (!listResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-friends-inventory-requests-error-load")
      return
    }

    for (data in listResponse.body()!!) {
      addItem(
        RequestHeadWithActionItem(
          player,
          friendProfile,
          data,
          friendProfile.receivedRequests.find { it.sender == data.uuid }!!
        )
      )
    }
  }
}