package net.vellity.service.economy.user

import net.vellity.utils.context.Context
import java.util.*

interface UserBalanceService {
  fun getBalance(player: UUID, currencyId: Int): UserBalance

  fun getAllBalancesInContext(player: UUID, context: Context): List<UserBalance>

  fun updateBalance(player: UUID, currencyId: Int, amount: Double)
}