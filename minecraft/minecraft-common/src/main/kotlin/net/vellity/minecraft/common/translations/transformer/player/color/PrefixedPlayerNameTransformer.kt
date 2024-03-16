package net.vellity.minecraft.common.translations.transformer.player.color

import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import java.util.*

class PrefixedPlayerNameTransformer : TypeTransformer<PrefixedPlayerName> {
  override fun toString(instance: Any, locale: Locale): String =
    (instance as PrefixedPlayerName).getPrefixedName()

  override fun fallback(locale: Locale): String =
    "Unknown player"

  override fun supports(clazz: Class<*>): Boolean =
    PrefixedPlayerName::class.java.isAssignableFrom(clazz)
}