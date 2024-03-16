package net.vellity.service.economy.transaction

import java.util.*

interface TransactionRepository {
  fun createTransaction(playerId: UUID, amount: Double, currencyId: Int, action: Action): Transaction

  fun revokeTransaction(transactionId: Int)

  fun getLastTransactions(playerId: UUID, currencyId: Int, limit: Int): List<Transaction>

  fun getTransaction(transactionId: Int): Transaction?
}