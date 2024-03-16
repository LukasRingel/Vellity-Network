package net.vellity.minecraft.common.spigot.friends.menu.outline

import net.vellity.minecraft.common.spigot.chat.input.UserInputService
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedTitle
import net.vellity.minecraft.common.spigot.extensions.sync
import net.vellity.minecraft.common.spigot.friends.action.FriendAddAction
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.minecraft.common.spigot.item.textures.Textures
import net.vellity.service.friends.FriendProfile
import org.bukkit.entity.Player

class FriendAddItem(player: Player, friendProfile: FriendProfile) : GuiItem(
  ItemFactory.playerHeadWithTexture(Textures.PLUS_OAK)
    .name("commons-friends-inventory-outline-item-add-name")
    .lore("commons-friends-inventory-outline-item-add-lore")
    .build(player),
  {
    sync { player.closeInventory() }
    player.sendTranslatedTitle(
      "commons-friends-inventory-outline-item-add-title-big",
      "commons-friends-inventory-outline-item-add-title-small"
    )
    UserInputService.awaitInput(player) {
      name -> FriendAddAction(player, name, friendProfile).run()
    }
  }
)
