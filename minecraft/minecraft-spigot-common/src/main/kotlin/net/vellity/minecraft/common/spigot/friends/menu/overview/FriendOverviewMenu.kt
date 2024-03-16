package net.vellity.minecraft.common.spigot.friends.menu.overview

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.spigot.friends.menu.FriendMenu
import net.vellity.minecraft.common.usercacheClient
import net.vellity.service.friends.FriendProfile
import net.vellity.service.usercache.session.UserSession
import org.bukkit.entity.Player
import java.util.*

class FriendOverviewMenu(player: Player, friendProfile: FriendProfile) :
  FriendMenu(player, friendProfile, "overview") {
  override fun drawContent() {
    if (friendProfile.friends.isEmpty()) {
      return
    }

    val listResponse = usercacheClient.combined()
      .bulkGetTexturesAndName(friendProfile.friends.map { it.friend })
      .execute()

    if (!listResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-friends-inventory-overview-error-load")
      return
    }

    for (data in listResponse.body()!!) {
      val friendship = friendProfile.friends.find { it.friend == data.uuid }!!
      val session: Optional<UserSession> = currentSessionOfFriend(friendship.friend)
      addItem(
        if (session.isPresent) {
          OnlineFriendHeadItem(player, data, friendship, session.get(), friendProfile)
        } else {
          OfflineFriendHeadItem(player, data, friendship, friendProfile)
        }
      )
    }
  }

  override fun configure() {
    backToMainMenuItem = false
  }

  private fun currentSessionOfFriend(friendUuid: UUID): Optional<UserSession> {
    val response = usercacheClient.sessions().getCurrentSession(context, friendUuid).execute()
    if (!response.isSuccessful) {
      return Optional.empty()
    }
    return Optional.ofNullable(response.body())
  }
}