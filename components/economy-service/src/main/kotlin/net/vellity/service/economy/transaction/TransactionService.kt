package net.vellity.service.economy.transaction

import net.vellity.service.economy.transaction.blueprint.TransactionCreateBlueprint
import java.util.*

interface TransactionService {
  fun createTransaction(blueprint: TransactionCreateBlueprint): Transaction

  fun revokeTransaction(transactionId: Int)

  fun getLastTransactions(playerId: UUID, currencyId: Int, limit: Int): List<Transaction>
}