package net.vellity.minecraft.common.spigot.friends.menu.overview

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.vellity.minecraft.common.spigot.friends.menu.information.FriendInformationMenu
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.translations.transformer.date.FormattedDateTime
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.minecraft.common.translations.transformer.player.color.PrefixedPlayerName
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.service.friends.FriendProfile
import net.vellity.service.friends.Friendship
import net.vellity.service.friends.FriendshipState
import net.vellity.service.usercache.combined.TextureAndName
import org.bukkit.entity.Player
import java.util.*

class OfflineFriendHeadItem(player: Player, textureAndName: TextureAndName, friendship: Friendship, friendProfile: FriendProfile) : GuiItem(
  ItemFactory.playerHeadWithTexture(textureAndName.textureValue)
    .name("commons-friends-inventory-overview-item-friend-offline-name")
    .lore("commons-friends-inventory-overview-item-friend-offline-lore")
    .replacements(
      "name",
      ColoredPlayerName.from(textureAndName.name, friendship.friend, player.uniqueId),
      "since",
      FormattedDateTime.timeAndDate(friendship.createdAt),
      "statusSuffix",
      buildFriendshipStateSuffix(friendship)
    )
    .build(player),
  { _ -> FriendInformationMenu(player, friendProfile, friendship, textureAndName, Optional.empty()).open() }
) {
  companion object {
    private fun buildFriendshipStateSuffix(friendship: Friendship) =
      Component.text(if (friendship.state == FriendshipState.FAVORITE) " ♥" else "", NamedTextColor.GOLD)
  }
}