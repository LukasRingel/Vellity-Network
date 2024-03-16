package net.vellity.minecraft.common.spigot.friends.action

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.friendsClient
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.minecraft.common.usercacheClient
import net.vellity.minecraft.common.validator.Validators
import net.vellity.service.friends.FriendProfile
import net.vellity.service.usercache.combined.TextureAndName
import net.vellity.utils.configuration.lifetimeDateMillis
import org.bukkit.entity.Player
import retrofit2.Response
import java.util.*

class BlocklistAddAction(
  private val player: Player,
  private val friendProfile: FriendProfile,
  private val name: String
) {
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
    if (alreadyBlocked(uuid)) {
      player.sendTranslatedMessage("commons-friends-validator-name-already-blocked")
      return
    }

    if (isFriendWith(uuid)) {
      FriendDeleteAction(
        friendProfile.friends.first { it.friend == uuid },
        player,
        TextureAndName.from(uuid, name)
      ).run()
    }

    val blocklistEntryResponse = friendsClient.friendships()
      .addToBlocklist(context, player.uniqueId, uuid, lifetimeDateMillis)
      .execute()

    if (!blocklistEntryResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-friend-action-blocked-send-failure")
      return
    }

    player.sendTranslatedMessage(
      "commons-friend-action-blocked-send-player",
      "name",
      ColoredPlayerName.from(name, uuid)
    )
  }

  private fun isFriendWith(uuid: UUID) =
    friendProfile.friends.any { it.friend == uuid }

  private fun alreadyBlocked(uuid: UUID) =
    friendProfile.blockedPlayer.any { it.target == uuid }

  private fun tryFindUuidByName(name: String): Response<UUID> =
    usercacheClient.names().getUuidByName(name).execute()

  private fun interactsWithSelf(player: Player, name: String) =
    player.name.equals(name, true)

  private fun notAValidMinecraftName(name: String) =
    !Validators.isValidMinecraftName(name)
}