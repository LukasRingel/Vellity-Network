package net.vellity.minecraft.common.translations.transformer.data

import net.vellity.minecraft.common.translations.transformer.Transformation
import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import java.util.*

class SeperatedIterableTransformer : TypeTransformer<SeperatedIterable> {
  override fun toString(instance: Any, locale: Locale): String {
    val seperatedIterable = instance as SeperatedIterable

    if (seperatedIterable.iterable.count() == 0) {
      return ""
    }

    if (seperatedIterable.iterable.count() == 1) {
      return Transformation.transform(seperatedIterable.iterable.first(), locale)
    }

    return seperatedIterable.iterable.joinToString(seperatedIterable.seperator) {
      seperatedIterable.decorate(Transformation.transform(it, locale))
    }
  }

  override fun fallback(locale: Locale): String =
    "N/A"

  override fun supports(clazz: Class<*>): Boolean =
    SeperatedIterable::class.java.isAssignableFrom(clazz)
}