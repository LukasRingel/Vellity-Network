package net.vellity.service.economy.user

import java.util.UUID

interface UserBalanceRepository {
  fun getBalance(player: UUID, currencyId: Int): UserBalance?

  fun getAllBalancesInContext(player: UUID, contextId: Int): List<UserBalance>

  fun updateBalance(player: UUID, currencyId: Int, amount: Double, default: Double)

  fun getActiveBooster(player: UUID, currencyId: Int): List<UserBooster>
}