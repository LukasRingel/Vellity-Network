package net.vellity.minecraft.common.spigot.friends.action

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.friendsClient
import net.vellity.service.usercache.combined.TextureAndName
import java.util.*

class RequestDenyAction(
  private val player: UUID,
  private val sender: UUID,
  private val senderTextureAndName: TextureAndName
) {
  fun run(): Boolean {
    return friendsClient.friendships()
      .denyFriendshipRequest(context, player, sender)
      .execute()
      .isSuccessful
  }
}