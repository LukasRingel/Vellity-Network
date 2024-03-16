package net.vellity.minecraft.common.spigot.friends.menu.settings.blocked

import net.vellity.minecraft.common.spigot.friends.action.BlocklistRemoveAction
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.minecraft.common.translations.transformer.date.FormattedDateTime
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.service.friends.BlocklistEntry
import net.vellity.service.friends.FriendProfile
import net.vellity.service.usercache.combined.TextureAndName
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

class BlockedPlayerItem(player: Player, friendProfile: FriendProfile, textureAndName: TextureAndName, entry: BlocklistEntry) : GuiItem(
  ItemFactory.playerHeadWithTexture(textureAndName.textureValue)
    .name("commons-friends-inventory-blocked-item-entry-name")
    .lore("commons-friends-inventory-blocked-item-entry-lore")
    .replacements(
      "name",
      ColoredPlayerName.from(textureAndName.name, textureAndName.uuid, player.uniqueId),
      "since",
      FormattedDateTime.auto(entry.createdAt),
      "until",
      FormattedDateTime.auto(entry.activeUntil)
    ).build(player),
  {
    if (it.click == ClickType.RIGHT) {
      BlocklistRemoveAction(player, friendProfile, textureAndName).run()
    }
  }
)