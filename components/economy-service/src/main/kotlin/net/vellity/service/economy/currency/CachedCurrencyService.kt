package net.vellity.service.economy.currency

import net.vellity.service.economy.currency.blueprint.CurrencyBoosterCreateBlueprint
import net.vellity.service.economy.currency.blueprint.CurrencyCreateBlueprint
import net.vellity.utils.context.Context
import net.vellity.utils.redis.RedisConnection
import net.vellity.utils.redis.RedisSynchronizer
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class CachedCurrencyService(
  private val repository: CurrencyRepository,
  redisConnection: RedisConnection
) : CurrencyService {


  private val synchronizer = RedisSynchronizer(redisConnection, "economy:currencies") {
    updateLocalState()
  }

  private var currencies: List<Currency> = repository.getAllCurrencies()

  private fun updateLocalState() {
    currencies = repository.getAllCurrencies()
  }

  private fun deployUpdate() {
    synchronizer.deployUpdate()
  }

  override fun getCurrencies(context: Context): List<Currency> {
    return currencies.filter { it.context == context }
  }

  override fun getAllCurrencies(): List<Currency> {
    return currencies
  }

  override fun getCurrencyById(id: Int): Currency {
    return currencies.first { it.id == id }
  }

  override fun createCurrency(blueprint: CurrencyCreateBlueprint): Currency {
    repository.createCurrency(blueprint.context, blueprint.name, blueprint.defaultValue)
      .let { currency ->
        deployUpdate()
        return currency
      }
  }

  override fun createBooster(blueprint: CurrencyBoosterCreateBlueprint): CurrencyBooster {
    repository.createBooster(
      blueprint.currencyId,
      blueprint.amount,
      Instant.ofEpochMilli(blueprint.startAt),
      Instant.ofEpochMilli(blueprint.activeUntil)
    ).let { currency ->
      deployUpdate()
      return currency
    }
  }
}