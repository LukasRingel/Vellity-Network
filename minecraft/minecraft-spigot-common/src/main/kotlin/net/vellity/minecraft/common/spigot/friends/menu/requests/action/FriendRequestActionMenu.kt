package net.vellity.minecraft.common.spigot.friends.menu.requests.action

import net.vellity.minecraft.common.spigot.friends.menu.FriendMenu
import net.vellity.minecraft.common.spigot.friends.menu.requests.RequestHeadWithoutActionItem
import net.vellity.service.friends.FriendProfile
import net.vellity.service.friends.FriendRequest
import net.vellity.service.usercache.combined.TextureAndName
import org.bukkit.entity.Player

class FriendRequestActionMenu(
  player: Player,
  friendProfile: FriendProfile,
  private val friendRequest: FriendRequest,
  private val senderTextureAndName: TextureAndName
) : FriendMenu(player, friendProfile, "requests-action") {
  override fun drawContent() {
    setItem(4, 2, RequestHeadWithoutActionItem(player, senderTextureAndName, friendRequest))
    setItem(2, 3, RequestAcceptItem(player, friendRequest, senderTextureAndName))
    setItem(6, 3, RequestDenyItem(player, friendRequest, senderTextureAndName))
  }
}