package net.vellity.minecraft.common.translations.transformer.player.currency

import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import net.vellity.service.economy.currency.Currency
import java.util.*

class CurrencyTransformer: TypeTransformer<Currency> {
  override fun toString(instance: Any, locale: Locale): String =
    TranslationRepository.get("commons-currency-${(instance as Currency).name}", locale)

  override fun fallback(locale: Locale): String =
    "Unknown currency"

  override fun supports(clazz: Class<*>): Boolean =
    Currency::class.java.isAssignableFrom(clazz)
}