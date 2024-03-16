package net.vellity.minecraft.common.spigot.friends.action

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.friendsClient
import net.vellity.minecraft.common.identity
import net.vellity.minecraft.common.spigot.extensions.PlayerUtilityExtensions.sendToServer
import net.vellity.service.friends.Setting
import net.vellity.service.usercache.session.UserSession
import org.bukkit.entity.Player
import java.util.*

class FriendJumpAction(
  private val player: Player,
  private val friend: UUID,
  private val userSession: UserSession,
  private val knowIfFriends: Boolean
) {
  fun run(): JumpResult {
    if (userSession.currentServer == identity.id) {
      return JumpResult.ERROR_SAME_SERVER
    }

    if (jumpDisabled(player, friend, knowIfFriends)) {
      return JumpResult.ERROR_DISABLED
    }

    player.sendToServer(userSession.currentServer)

    return JumpResult.SUCCESS
  }

  private fun jumpDisabled(player: Player, friend: UUID, knowIfFriends: Boolean): Boolean {
    val response = friendsClient.settings()
      .checkSetting(context, friend, Setting.FRIENDS_CAN_JUMP, player.uniqueId, knowIfFriends)
      .execute()
    return if (!response.isSuccessful) {
      true
    } else {
      !response.body()!!
    }
  }

  enum class JumpResult {
    ERROR_SAME_SERVER,
    ERROR_HTTP,
    ERROR_DISABLED,
    SUCCESS
  }
}