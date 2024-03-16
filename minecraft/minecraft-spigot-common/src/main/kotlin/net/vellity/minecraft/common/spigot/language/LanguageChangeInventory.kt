package net.vellity.minecraft.common.spigot.language

import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.translate
import net.vellity.minecraft.common.spigot.gui.Gui
import net.vellity.minecraft.common.spigot.gui.Mask
import net.vellity.minecraft.common.spigot.gui.Masks
import org.bukkit.entity.Player

class LanguageChangeInventory(player: Player) :
  Gui(player, player.translate("commons-inventory-language-title"), 3) {
  override fun redraw() {
    fillSpacerFromMask(
      Mask.builder(3)
        .pattern(Masks.THREE_ROWS_WITH_BORDER)
        .build(this)
    )
    for (languageOption in LanguageOption.values()) {
      addItem(LanguageItem(languageOption, player))
    }
  }
}