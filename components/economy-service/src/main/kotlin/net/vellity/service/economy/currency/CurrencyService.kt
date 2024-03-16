package net.vellity.service.economy.currency

import net.vellity.service.economy.currency.blueprint.CurrencyBoosterCreateBlueprint
import net.vellity.service.economy.currency.blueprint.CurrencyCreateBlueprint
import net.vellity.utils.context.Context

interface CurrencyService {
  fun getCurrencies(context: Context): List<Currency>

  fun getAllCurrencies(): List<Currency>

  fun getCurrencyById(id: Int): Currency

  fun createCurrency(blueprint: CurrencyCreateBlueprint): Currency

  fun createBooster(blueprint: CurrencyBoosterCreateBlueprint): CurrencyBooster
}