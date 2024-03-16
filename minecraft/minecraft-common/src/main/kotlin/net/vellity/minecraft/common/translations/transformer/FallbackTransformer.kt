package net.vellity.minecraft.common.translations.transformer

import java.util.*

class FallbackTransformer : TypeTransformer<Any> {
  override fun toString(instance: Any, locale: Locale): String = instance.toString()

  override fun fallback(locale: Locale): String = "N/A"

  override fun supports(clazz: Class<*>): Boolean = true
}