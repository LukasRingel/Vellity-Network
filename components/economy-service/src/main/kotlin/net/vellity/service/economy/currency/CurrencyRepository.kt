package net.vellity.service.economy.currency

import net.vellity.utils.context.Context
import java.time.Instant

interface CurrencyRepository {
  fun getCurrencies(context: Context): List<Currency>

  fun getAllCurrencies(): List<Currency>

  fun createCurrency(context: Context, name: String, defaultValue: Double): Currency

  fun createBooster(currencyId: Int, amount: Double, startAt: Instant, activeUntil: Instant): CurrencyBooster
}