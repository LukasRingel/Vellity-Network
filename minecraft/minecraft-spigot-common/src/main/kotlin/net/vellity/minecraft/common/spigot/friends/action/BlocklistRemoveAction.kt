package net.vellity.minecraft.common.spigot.friends.action

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.friendsClient
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.service.friends.FriendProfile
import net.vellity.service.usercache.combined.TextureAndName
import org.bukkit.entity.Player

class BlocklistRemoveAction(
  private val player: Player,
  private val friendProfile: FriendProfile,
  private val friendTextureAndName: TextureAndName
) {
  fun run() {
    if (!friendProfile.blockedPlayer.any()) {
      player.sendTranslatedMessage("commons-friends-validator-name-not-blocked", "name", friendTextureAndName.name)
      return
    }

    val response = friendsClient.friendships()
      .removeFromBlocklist(context, player.uniqueId, friendTextureAndName.uuid)
      .execute()

    friendProfile.blockedPlayer.removeIf { it.target == friendTextureAndName.uuid }

    if (!response.isSuccessful) {
      player.sendTranslatedMessage("commons-friend-action-blocked-remove-error")
      return
    }

    player.sendTranslatedMessage(
      "commons-friend-action-blocked-remove-success",
      "name",
      ColoredPlayerName.from(friendTextureAndName)
    )
  }
}