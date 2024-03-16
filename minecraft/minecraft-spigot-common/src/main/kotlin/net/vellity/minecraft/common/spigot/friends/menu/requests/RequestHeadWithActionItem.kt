package net.vellity.minecraft.common.spigot.friends.menu.requests

import net.vellity.minecraft.common.spigot.friends.menu.requests.action.FriendRequestActionMenu
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.minecraft.common.translations.transformer.date.FormattedDateTime
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.service.friends.FriendProfile
import net.vellity.service.friends.FriendRequest
import net.vellity.service.usercache.combined.TextureAndName
import org.bukkit.entity.Player

class RequestHeadWithActionItem(player: Player, friendProfile: FriendProfile, textureAndName: TextureAndName, friendRequest: FriendRequest) : GuiItem(
  ItemFactory.playerHeadWithTexture(textureAndName.textureValue)
    .name("commons-friends-inventory-requests-item-request-name")
    .lore("commons-friends-inventory-requests-item-request-lore")
    .replacements(
      "name",
      ColoredPlayerName.from(textureAndName.name, textureAndName.uuid, player.uniqueId),
      "requestDate",
      FormattedDateTime.timeAndDate(friendRequest.createdAt)
    ).build(player),
  { FriendRequestActionMenu(player, friendProfile, friendRequest, textureAndName).open() }
)