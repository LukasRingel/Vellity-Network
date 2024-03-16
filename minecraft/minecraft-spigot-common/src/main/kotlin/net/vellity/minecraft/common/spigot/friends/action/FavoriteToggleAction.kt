package net.vellity.minecraft.common.spigot.friends.action

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.friendsClient
import net.vellity.service.friends.Friendship
import net.vellity.service.friends.FriendshipState
import org.bukkit.entity.Player

class FavoriteToggleAction(
  private val player: Player,
  private val friendship: Friendship,
  private val newState: FriendshipState
) {
  fun run(): Boolean {
    return friendsClient.friendships()
      .updateFriendshipState(context, player.uniqueId, friendship.friend, newState)
      .execute().isSuccessful
  }
}