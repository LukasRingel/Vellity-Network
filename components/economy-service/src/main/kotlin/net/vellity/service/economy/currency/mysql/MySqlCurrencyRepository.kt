package net.vellity.service.economy.currency.mysql

import net.vellity.service.economy.currency.Currency
import net.vellity.service.economy.currency.CurrencyBooster
import net.vellity.service.economy.currency.CurrencyRepository
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class MySqlCurrencyRepository(private val mySqlConnection: MySqlConnection) : CurrencyRepository {
  override fun getCurrencies(context: Context): List<Currency> {
    val currencies = mySqlConnection.executeQuery(SelectCurrenciesForContext(context))
    for (currency in currencies) {
      currency.booster(mySqlConnection.executeQuery(SelectAllBoosterForCurrency(currency.id)))
    }
    return currencies
  }

  override fun getAllCurrencies(): List<Currency> {
    val currencies = mySqlConnection.executeQuery(SelectAllCurrencies())
    for (currency in currencies) {
      currency.booster(mySqlConnection.executeQuery(SelectAllBoosterForCurrency(currency.id)))
    }
    return currencies
  }

  override fun createCurrency(context: Context, name: String, defaultValue: Double): Currency {
    mySqlConnection.executeUpdateAndGetId(InsertCurrency(context, name, defaultValue)).let { id ->
      return Currency(id, context, name, defaultValue, mutableListOf())
    }
  }

  override fun createBooster(currencyId: Int, amount: Double, startAt: Instant, activeUntil: Instant): CurrencyBooster {
    mySqlConnection.executeUpdateAndGetId(
      InsertCurrencyBooster(currencyId, amount, startAt, activeUntil)
    ).let { id ->
      return CurrencyBooster(id, currencyId, amount, startAt, activeUntil)
    }
  }
}