package net.vellity.service.economy.user

import net.vellity.service.economy.currency.CurrencyService
import net.vellity.utils.context.Context
import net.vellity.utils.context.Context.Companion.id
import net.vellity.utils.mysql.extensions.nowUtc
import org.springframework.stereotype.Service
import java.util.*

@Service
class DirectUserBalanceService(
  private val userBalanceRepository: UserBalanceRepository,
  private val currencyService: CurrencyService
) : UserBalanceService {
  override fun getBalance(player: UUID, currencyId: Int): UserBalance {
    val userBalance = userBalanceRepository.getBalance(player, currencyId)

    if (userBalance != null) {
      return userBalance
    }

    currencyService.getCurrencyById(currencyId).let { currency ->
      return UserBalance(0, player, currency.defaultBalance, currencyId)
    }
  }

  override fun getAllBalancesInContext(player: UUID, context: Context): List<UserBalance> {
    userBalanceRepository.getAllBalancesInContext(player, context.id()).let { databaseBalance ->
      val balances = mutableListOf<UserBalance>()
      balances.addAll(databaseBalance)

      val currenciesInContext = currencyService.getCurrencies(Context.valueOf(context.id()))
      for (currency in currenciesInContext) {
        if (balances.none { it.currency == currency.id }) {
          balances.add(UserBalance(0, player, currency.defaultBalance, currency.id))
        }
      }

      return balances
    }
  }

  override fun updateBalance(player: UUID, currencyId: Int, amount: Double) {
    var amountWithBoosters = amount
    userBalanceRepository.getActiveBooster(player, currencyId).forEach { booster ->
      if (booster.activeUntil.isBefore(nowUtc())) {
        amountWithBoosters *= booster.amount
      }
    }
    currencyService.getCurrencyById(currencyId).let { currency ->
      currency.activeBoosters().forEach { booster ->
        if (booster.activeUntil.isBefore(nowUtc())) {
          amountWithBoosters *= booster.amount
        }
      }
    }
    userBalanceRepository.updateBalance(
      player,
      currencyId,
      amountWithBoosters,
      currencyService.getCurrencyById(currencyId).defaultBalance
    )
  }
}