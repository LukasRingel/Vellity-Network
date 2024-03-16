package net.vellity.minecraft.common.spigot.chat.format

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.translations.format.MiniMessageFormat
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import org.bukkit.entity.Player
import java.util.*

class ColoredDisplayNameFormat : MessageFormat {
  override fun format(senderUuid: UUID, senderName: String, message: String, viewer: Player): Component =
    Component.empty()
      .append(formatPlayerName(senderName, senderUuid, viewer))
      .append(Component.text(" » ", NamedTextColor.DARK_GRAY))
      .append(Component.text(message, TranslationRepository.getColor("chat")))

  private fun formatPlayerName(senderName: String, senderUuid: UUID, viewer: Player) = MiniMessageFormat.format(
    ColoredPlayerName.from(senderName, senderUuid, viewer.uniqueId).getColoredName()
  )
}