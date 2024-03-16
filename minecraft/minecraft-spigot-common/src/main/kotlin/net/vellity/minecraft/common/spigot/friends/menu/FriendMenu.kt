package net.vellity.minecraft.common.spigot.friends.menu

import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.translate
import net.vellity.minecraft.common.spigot.friends.menu.outline.BackToMainMenu
import net.vellity.minecraft.common.spigot.friends.menu.outline.FriendAddItem
import net.vellity.minecraft.common.spigot.friends.menu.outline.RequestsItem
import net.vellity.minecraft.common.spigot.friends.menu.outline.SettingsItem
import net.vellity.minecraft.common.spigot.gui.Gui
import net.vellity.minecraft.common.spigot.gui.Mask
import net.vellity.minecraft.common.spigot.gui.Masks
import net.vellity.service.friends.FriendProfile
import org.bukkit.entity.Player

abstract class FriendMenu(
  private val player: Player,
  val friendProfile: FriendProfile,
  title: String
) : Gui(player, player.translate("commons-friends-inventory-$title-title"), 6) {

  var backToMainMenuItem = true
  var openRequestsItem = true
  var settingsItem = true
  var friendAddItem = true

  override fun redraw() {
    configure()
    drawOutline()
    drawContent()
  }

  private fun drawOutline() {
    fillSpacerFromMask(
      Mask.builder(6)
        .pattern(Masks.SIX_ROWS_WITH_BORDER)
        .build(this)
    )

    if (backToMainMenuItem) {
      setItem(0, 5, BackToMainMenu(player, friendProfile))
    }

    if (openRequestsItem) {
      setItem(2, 5, RequestsItem(player, friendProfile))
    }

    if (settingsItem) {
      setItem(6, 5, SettingsItem(player, friendProfile))
    }

    if (friendAddItem) {
      setItem(4, 5, FriendAddItem(player, friendProfile))
    }
  }

  abstract fun drawContent()

  open fun configure() {
  }
}