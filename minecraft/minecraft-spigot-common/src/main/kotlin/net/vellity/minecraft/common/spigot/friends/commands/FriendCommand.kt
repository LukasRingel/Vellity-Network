package net.vellity.minecraft.common.spigot.friends.commands

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.friendsClient
import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.spigot.friends.menu.overview.FriendOverviewMenu
import net.vellity.service.friends.FriendProfile
import org.bukkit.entity.Player

class FriendCommand : PlayerCommand(
  "friend",
  "Manage your friendships",
  aliases = mutableListOf("friends")
) {
  override fun executeCommand(player: Player, args: Array<out String>) {
      getFriendProfileOrSendErrorMessage(player)?.let {
        FriendOverviewMenu(player, it).open()
      }
  }

  private fun getFriendProfileOrSendErrorMessage(player: Player): FriendProfile? {
    val response = friendsClient.profiles().getFriendProfile(context, player.uniqueId).execute()
    if (!response.isSuccessful) {
      player.sendTranslatedMessage("commons-friends-inventory-overview-error-load")
      return null
    }
    return response.body()
  }
}