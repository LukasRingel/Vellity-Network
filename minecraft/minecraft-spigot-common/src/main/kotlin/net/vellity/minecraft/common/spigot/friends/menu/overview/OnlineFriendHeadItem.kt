package net.vellity.minecraft.common.spigot.friends.menu.overview

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.vellity.minecraft.common.spigot.friends.menu.information.FriendInformationMenu
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.translations.transformer.date.FormattedDateTime
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.minecraft.common.translations.transformer.player.color.PrefixedPlayerName
import net.vellity.minecraft.common.translations.transformer.server.ServerGroupDisplay
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.service.friends.FriendProfile
import net.vellity.service.friends.Friendship
import net.vellity.service.friends.FriendshipState
import net.vellity.service.usercache.combined.TextureAndName
import net.vellity.service.usercache.session.UserSession
import org.bukkit.entity.Player
import java.util.*

class OnlineFriendHeadItem(
  player: Player,
  textureAndName: TextureAndName,
  friendship: Friendship,
  session: UserSession,
  friendProfile: FriendProfile
) : GuiItem(
  ItemFactory.playerHeadWithTexture(textureAndName.textureValue)
    .name("commons-friends-inventory-overview-item-friend-online-name")
    .lore("commons-friends-inventory-overview-item-friend-online-lore")
    .replacements(
      "name",
      ColoredPlayerName.from(textureAndName.name, friendship.friend, player.uniqueId),
      "since",
      FormattedDateTime.timeAndDate(friendship.createdAt),
      "statusSuffix",
      buildFriendshipStateSuffix(friendship),
      "serverGroup",
      ServerGroupDisplay.fromIdentity(session.currentServer)
    )
    .build(player),
  { _ -> FriendInformationMenu(player, friendProfile, friendship, textureAndName, Optional.of(session)).open() }
) {
  companion object {
    private fun buildFriendshipStateSuffix(friendship: Friendship) =
      Component.text(if (friendship.state == FriendshipState.FAVORITE) " ♥" else "", NamedTextColor.GOLD)
  }
}