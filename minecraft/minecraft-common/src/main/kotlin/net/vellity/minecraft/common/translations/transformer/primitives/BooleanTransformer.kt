package net.vellity.minecraft.common.translations.transformer.primitives

import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import java.util.*

class BooleanTransformer : TypeTransformer<Boolean> {
  override fun toString(instance: Any, locale: Locale): String =
    translate("phrase-boolean-$instance", locale)

  override fun fallback(locale: Locale): String =
    translate("phase-boolean-false", locale)

  override fun supports(clazz: Class<*>): Boolean =
    Boolean::class.java.isAssignableFrom(clazz)
}