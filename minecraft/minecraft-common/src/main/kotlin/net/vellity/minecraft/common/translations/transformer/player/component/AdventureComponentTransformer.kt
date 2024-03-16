package net.vellity.minecraft.common.translations.transformer.player.component

import net.kyori.adventure.text.Component
import net.vellity.minecraft.common.translations.format.MiniMessageFormat
import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import java.util.*

class AdventureComponentTransformer : TypeTransformer<Component> {
  override fun toString(instance: Any, locale: Locale): String =
    MiniMessageFormat.componentToString((instance as Component))

  override fun fallback(locale: Locale): String =
    "Unknown Text Component"

  override fun supports(clazz: Class<*>): Boolean =
    Component::class.java.isAssignableFrom(clazz)
}