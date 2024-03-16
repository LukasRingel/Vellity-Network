package net.vellity.minecraft.common.translations.transformer

import java.util.*

object Transformation {
  fun findTransformerFor(clazz: Class<*>): TypeTransformer<*> =
    TypeTransformerRegistry.getConverter(clazz) ?: FallbackTransformer()

  fun <T> transform(instance: T, locale: Locale): String {
    if (instance == null) {
      return ""
    }

    if (instance is String) {
      return instance
    }

    return findTransformerFor(instance.javaClass).toString(instance, locale)
  }
}