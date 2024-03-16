package net.vellity.minecraft.common.translations.transformer.data

import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import net.vellity.service.babbel.LocaleConverter
import java.util.*

class LocaleTransformer : TypeTransformer<Locale> {
  override fun toString(instance: Any, locale: Locale): String =
    TranslationRepository.get("commons-language-" +
      LocaleConverter.localeToString(instance as Locale)
        .replace("_", "-")
        .lowercase(),
      locale)

  override fun fallback(locale: Locale): String =
    "Unknown language"

  override fun supports(clazz: Class<*>): Boolean =
    Locale::class.java.isAssignableFrom(clazz)
}