package net.vellity.minecraft.common.spigot.friends.menu.information

import net.vellity.minecraft.common.spigot.friends.menu.FriendMenu
import net.vellity.minecraft.common.spigot.friends.menu.information.favorite.FriendDemoteToNormalItem
import net.vellity.minecraft.common.spigot.friends.menu.information.favorite.FriendPromoteToFavoriteItem
import net.vellity.minecraft.common.spigot.friends.menu.information.jump.JumpToOfflineItem
import net.vellity.minecraft.common.spigot.friends.menu.information.jump.JumpToOnlineItem
import net.vellity.service.friends.FriendProfile
import net.vellity.service.friends.Friendship
import net.vellity.service.friends.FriendshipState
import net.vellity.service.usercache.combined.TextureAndName
import net.vellity.service.usercache.session.UserSession
import org.bukkit.entity.Player
import java.util.*

class FriendInformationMenu(
  player: Player,
  friendProfile: FriendProfile,
  private val friendship: Friendship,
  private val textureAndName: TextureAndName,
  private val friendSession: Optional<UserSession>
) :
  FriendMenu(player, friendProfile, "information") {
  override fun drawContent() {
    setItem(6, 3, getFavoriteItem())
    setItem(4, 3, FriendDeleteItem(player, friendship, textureAndName))
    setItem(2, 3, getJumpToItem())
    setItem(4, 2, FriendSkullWithNameItem(player, textureAndName))
  }

  private fun getFavoriteItem() = if (friendship.state == FriendshipState.NORMAL)
    FriendPromoteToFavoriteItem(player, friendship, this) else
    FriendDemoteToNormalItem(player, friendship, this)

  private fun getJumpToItem() = if (!friendSession.isPresent)
    JumpToOfflineItem(player, textureAndName) else
    JumpToOnlineItem(player, textureAndName, friendSession.get())
}