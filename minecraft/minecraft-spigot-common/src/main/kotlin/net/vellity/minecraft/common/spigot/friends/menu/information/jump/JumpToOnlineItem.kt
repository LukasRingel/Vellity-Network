package net.vellity.minecraft.common.spigot.friends.menu.information.jump

import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.spigot.friends.action.FriendJumpAction
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.minecraft.common.translations.transformer.server.ServerGroupDisplay
import net.vellity.service.usercache.combined.TextureAndName
import net.vellity.service.usercache.session.UserSession
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

class JumpToOnlineItem(player: Player, textureAndName: TextureAndName, session: UserSession) : GuiItem(
  ItemFactory.item(Material.DIAMOND_BOOTS)
    .name("commons-friends-inventory-information-item-jumpto-online-name")
    .lore("commons-friends-inventory-information-item-jumpto-online-lore")
    .replacements(
      "name",
      ColoredPlayerName.from(textureAndName.name, textureAndName.uuid, player.uniqueId),
      "serverGroup",
      ServerGroupDisplay.fromIdentity(session.currentServer)
    )
    .hideFlags(ItemFlag.HIDE_ATTRIBUTES)
    .build(player),
  { _ ->
    val result = FriendJumpAction(player, textureAndName.uuid, session, true).run()
    when (result) {
      FriendJumpAction.JumpResult.ERROR_SAME_SERVER -> {
        player.sendTranslatedMessage(
          "commons-friends-inventory-information-item-jumpto-online-error-same-server",
          "name",
          ColoredPlayerName.from(textureAndName.name, textureAndName.uuid, player.uniqueId)
        )
      }

      FriendJumpAction.JumpResult.ERROR_HTTP -> {
        player.sendTranslatedMessage("commons-friends-inventory-information-item-jumpto-online-error-http")
      }

      FriendJumpAction.JumpResult.ERROR_DISABLED -> {
        player.sendTranslatedMessage(
          "commons-friends-inventory-information-item-jumpto-online-error-disabled",
          "name",
          ColoredPlayerName.from(textureAndName.name, textureAndName.uuid, player.uniqueId)
        )
      }

      FriendJumpAction.JumpResult.SUCCESS -> { /* Do nothing */
      }

      else -> {
        player.sendTranslatedMessage("commons-friends-inventory-information-item-jumpto-online-error-unknown")
      }
    }
  }
)