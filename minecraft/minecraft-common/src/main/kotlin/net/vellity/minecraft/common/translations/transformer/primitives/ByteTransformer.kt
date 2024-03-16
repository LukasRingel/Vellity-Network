package net.vellity.minecraft.common.translations.transformer.primitives

import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import java.util.*

class ByteTransformer : TypeTransformer<Byte> {
  override fun toString(instance: Any, locale: Locale): String = instance.toString()

  override fun fallback(locale: Locale): String = "0"

  override fun supports(clazz: Class<*>): Boolean =
    Byte::class.java.isAssignableFrom(clazz)
}