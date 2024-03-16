package net.vellity.service.economy.user.mysql

import net.vellity.service.economy.user.UserBalance
import net.vellity.service.economy.user.UserBalanceRepository
import net.vellity.service.economy.user.UserBooster
import net.vellity.utils.mysql.MySqlConnection
import org.springframework.stereotype.Component
import java.util.*

@Component
class MySqlUserBalanceRepository(private val mySqlConnection: MySqlConnection) : UserBalanceRepository {
  override fun getBalance(player: UUID, currencyId: Int): UserBalance? {
    return mySqlConnection.executeQuery(SelectBalanceForCurrency(player, currencyId)).firstOrNull()
  }

  override fun getAllBalancesInContext(player: UUID, contextId: Int): List<UserBalance> {
    return mySqlConnection.executeQuery(SelectBalanceForContext(player, contextId))
  }

  override fun updateBalance(player: UUID, currencyId: Int, amount: Double, default: Double) {
    mySqlConnection.executeUpdate(UpdateBalanceForCurrency(player, currencyId, amount, default))
  }

  override fun getActiveBooster(player: UUID, currencyId: Int): List<UserBooster> {
    return mySqlConnection.executeQuery(SelectBoosterForPlayerForCurrency(player, currencyId))
  }
}