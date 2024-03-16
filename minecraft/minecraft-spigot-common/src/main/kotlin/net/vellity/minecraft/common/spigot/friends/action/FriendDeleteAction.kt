package net.vellity.minecraft.common.spigot.friends.action

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.friendsClient
import net.vellity.minecraft.common.spigot.chat.broadcast.BroadcastService
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.service.friends.Friendship
import net.vellity.service.usercache.combined.TextureAndName
import org.bukkit.entity.Player

class FriendDeleteAction(
  private val friendship: Friendship,
  private val player: Player,
  private val textureAndName: TextureAndName
) {
  fun run(): Boolean {
    val response = friendsClient.friendships()
      .deleteFriendshipWith(context, friendship.player, friendship.friend)
      .execute()

    if (!response.isSuccessful) {
      return false
    }

    player.sendTranslatedMessage(
      "commons-friend-action-friendship-delete-player",
      "name",
      ColoredPlayerName.from(textureAndName.name, textureAndName.uuid)
    )

    BroadcastService.sendToOnePlayer(
      textureAndName.uuid,
      "commons-friend-action-friendship-delete-target",
      mapOf("name" to ColoredPlayerName.from(player.name, player.uniqueId))
    )
    return true
  }
}