package net.vellity.minecraft.common.spigot.friends.action

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.friendsClient
import net.vellity.minecraft.common.spigot.chat.broadcast.BroadcastService
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.minecraft.common.usercacheClient
import net.vellity.minecraft.common.validator.Validators
import net.vellity.service.friends.FriendProfile
import net.vellity.service.friends.FriendRequest
import net.vellity.service.friends.Setting
import net.vellity.service.friends.SettingState
import org.bukkit.entity.Player
import retrofit2.Response
import java.util.*

class FriendAddAction(private val player: Player, private val name: String, private val friendProfile: FriendProfile) {
  fun run() {
    if (notAValidMinecraftName(name)) {
      player.sendTranslatedMessage("commons-friends-validator-name-minecraft", "name", name)
      return
    }

    if (interactsWithSelf(player, name)) {
      player.sendTranslatedMessage("commons-friends-validator-name-self")
      return
    }

    val response = tryFindUuidByName(name)
    if (!response.isSuccessful) {
      player.sendTranslatedMessage("commons-friends-validator-name-unknown", "name", name)
      return
    }

    val uuid = response.body()!!
    if (alreadyFriends(friendProfile, uuid)) {
      player.sendTranslatedMessage("commons-friends-validator-name-already-friend")
      return
    }

    if (hasAlreadySendARequestToTarget(uuid)) {
      player.sendTranslatedMessage(
        "commons-friends-validator-profile-requested",
        "name",
        ColoredPlayerName.from(name, uuid)
      )
      return
    }

    val friendProfileResponse = friendProfileOfFriend(uuid)
    if (!friendProfileResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-friends-validator-profile-error", "name", name)
      return
    }

    val friendProfileOfFriend = friendProfileResponse.body()!!
    if (targetDoesNotAcceptRequests(friendProfileOfFriend)) {
      player.sendTranslatedMessage(
        "commons-friends-validator-profile-no-requests",
        "name",
        ColoredPlayerName.from(name, uuid)
      )
      return
    }

    if (wasBlockedByTarget(friendProfileOfFriend)) {
      player.sendTranslatedMessage(
        "commons-friends-validator-profile-blocked",
        "name",
        ColoredPlayerName.from(name, uuid)
      )
      return
    }

    val friendRequestResponse = tryCreateFriendshipRequest(uuid)
    if (!friendRequestResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-friends-validator-request-error")
      return
    }

    player.sendTranslatedMessage(
      "commons-friend-action-request-send-player",
      "name",
      ColoredPlayerName.from(name, uuid)
    )

    BroadcastService.sendToOnePlayer(
      uuid,
      "commons-friend-action-request-send-target",
      mapOf("name" to ColoredPlayerName.from(player.name, player.uniqueId))
    )
  }

  private fun tryCreateFriendshipRequest(uuid: UUID): Response<FriendRequest> =
    friendsClient.friendships().createFriendshipRequest(context, player.uniqueId, uuid).execute()

  private fun hasAlreadySendARequestToTarget(uuid: UUID) =
    friendProfile.sentRequests.any { it.target == uuid }

  private fun wasBlockedByTarget(friendProfileOfFriend: FriendProfile) =
    friendProfileOfFriend.blockedPlayer.any { it.target == player.uniqueId }

  private fun targetDoesNotAcceptRequests(friendProfileOfFriend: FriendProfile) =
    friendProfileOfFriend.getSettingState(Setting.REQUESTS) != SettingState.EVERYBODY

  private fun friendProfileOfFriend(uuid: UUID): Response<FriendProfile> =
    friendsClient.profiles().getFriendProfile(context, uuid).execute()

  private fun tryFindUuidByName(name: String): Response<UUID> =
    usercacheClient.names().getUuidByName(name).execute()

  private fun alreadyFriends(friendProfile: FriendProfile, uuid: UUID) =
    friendProfile.friends.any { it.friend == uuid }

  private fun interactsWithSelf(player: Player, name: String) =
    player.name.equals(name, true)

  private fun notAValidMinecraftName(name: String) =
    !Validators.isValidMinecraftName(name)
}