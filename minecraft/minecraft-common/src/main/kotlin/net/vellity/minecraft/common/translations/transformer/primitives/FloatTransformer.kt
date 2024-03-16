package net.vellity.minecraft.common.translations.transformer.primitives

import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import java.util.*

class FloatTransformer : TypeTransformer<Float> {
  override fun toString(instance: Any, locale: Locale): String = instance.toString()

  override fun fallback(locale: Locale): String = "0.0"

  override fun supports(clazz: Class<*>): Boolean =
    Float::class.java.isAssignableFrom(clazz)
}