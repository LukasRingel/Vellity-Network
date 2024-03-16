package net.vellity.minecraft.common.translations.transformer.primitives

import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import java.util.*

class IntegerTransformer : TypeTransformer<Int> {
  override fun toString(instance: Any, locale: Locale): String = instance.toString()

  override fun fallback(locale: Locale): String = "0"

  override fun supports(clazz: Class<*>): Boolean =
    Int::class.java.isAssignableFrom(clazz)
}