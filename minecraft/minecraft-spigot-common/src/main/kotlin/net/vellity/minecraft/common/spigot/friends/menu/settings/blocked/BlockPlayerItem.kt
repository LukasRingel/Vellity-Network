package net.vellity.minecraft.common.spigot.friends.menu.settings.blocked

import net.vellity.minecraft.common.spigot.chat.input.UserInputService
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedTitle
import net.vellity.minecraft.common.spigot.extensions.sync
import net.vellity.minecraft.common.spigot.friends.action.BlocklistAddAction
import net.vellity.minecraft.common.spigot.friends.action.FriendAddAction
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.minecraft.common.spigot.item.textures.Textures
import net.vellity.service.friends.FriendProfile
import org.bukkit.entity.Player

class BlockPlayerItem(player: Player, friendProfile: FriendProfile) : GuiItem(
  ItemFactory.playerHeadWithTexture(Textures.PLUS_RED)
    .name("commons-friends-inventory-blocked-item-add-name")
    .lore("commons-friends-inventory-blocked-item-add-lore")
    .build(player),
  {
    sync { player.closeInventory() }
    player.sendTranslatedTitle(
      "commons-friends-inventory-blocked-item-add-big",
      "commons-friends-inventory-blocked-item-add-small"
    )
    UserInputService.awaitInput(player) {
        name -> BlocklistAddAction(player, friendProfile, name).run()
    }
  }
)