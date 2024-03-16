package net.vellity.minecraft.common.translations.transformer.player.color

import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import java.util.*

class ColoredPlayerNameTransformer : TypeTransformer<ColoredPlayerName> {
  override fun toString(instance: Any, locale: Locale): String =
    (instance as ColoredPlayerName).getColoredName()

  override fun fallback(locale: Locale): String =
    "Unknown player"

  override fun supports(clazz: Class<*>): Boolean =
    ColoredPlayerName::class.java.isAssignableFrom(clazz)
}