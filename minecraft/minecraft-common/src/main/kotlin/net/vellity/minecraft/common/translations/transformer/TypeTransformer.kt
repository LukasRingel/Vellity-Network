package net.vellity.minecraft.common.translations.transformer

import net.vellity.minecraft.common.translations.TranslationRepository
import java.util.*

interface TypeTransformer<T> {
  fun toString(instance: Any, locale: Locale): String

  fun fallback(locale: Locale): String

  fun supports(clazz: Class<*>): Boolean

  fun translate(key: String, locale: Locale): String =
    TranslationRepository.get(key, locale)
}