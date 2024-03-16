package net.vellity.minecraft.common.spigot.friends.menu.requests.action

import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.spigot.extensions.sync
import net.vellity.minecraft.common.spigot.friends.action.RequestAcceptAction
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.minecraft.common.spigot.item.textures.Textures
import net.vellity.service.friends.FriendRequest
import net.vellity.service.usercache.combined.TextureAndName
import org.bukkit.entity.Player

class RequestAcceptItem(player: Player, request: FriendRequest, textureAndName: TextureAndName) : GuiItem(
  ItemFactory.playerHeadWithTexture(Textures.PLUS_GREEN)
    .name("commons-friends-inventory-requests-action-item-accept-name")
    .lore("commons-friends-inventory-requests-action-item-accept-lore")
    .replacements("name", textureAndName.name)
    .build(player),
  {
    RequestAcceptAction(player.uniqueId, request.sender, textureAndName).run()
    sync { player.closeInventory() }
  }
)