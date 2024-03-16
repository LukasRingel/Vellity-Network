package net.vellity.minecraft.common.spigot.friends.menu.requests.action

import net.vellity.minecraft.common.spigot.extensions.sync
import net.vellity.minecraft.common.spigot.friends.action.RequestAcceptAction
import net.vellity.minecraft.common.spigot.friends.action.RequestDenyAction
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.minecraft.common.spigot.item.textures.Textures
import net.vellity.service.friends.FriendRequest
import net.vellity.service.usercache.combined.TextureAndName
import org.bukkit.entity.Player

class RequestDenyItem(player: Player, request: FriendRequest, textureAndName: TextureAndName) : GuiItem(
  ItemFactory.playerHeadWithTexture(Textures.MINUS_RED)
    .name("commons-friends-inventory-requests-action-item-deny-name")
    .lore("commons-friends-inventory-requests-action-item-deny-lore")
    .replacements("name", textureAndName.name)
    .build(player),
  {
    RequestDenyAction(player.uniqueId, request.sender, textureAndName).run()
    sync { player.closeInventory() }
  }
)