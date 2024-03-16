package net.vellity.minecraft.common.translations.transformer.date

import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import java.util.*

class FormattedDateTimeTransformer : TypeTransformer<FormattedDateTime> {
  override fun toString(instance: Any, locale: Locale): String =
    (instance as FormattedDateTime).getFormattedDateTime(locale)

  override fun fallback(locale: Locale): String =
    "Unknown date"

  override fun supports(clazz: Class<*>): Boolean =
    FormattedDateTime::class.java.isAssignableFrom(clazz)
}