package net.vellity.service.economy.transaction.mysql

import net.vellity.service.economy.transaction.Action
import net.vellity.service.economy.transaction.Transaction
import net.vellity.service.economy.transaction.TransactionRepository
import net.vellity.utils.mysql.MySqlConnection
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class MySqlTransactionRepository(private val mySqlConnection: MySqlConnection) : TransactionRepository {
  override fun createTransaction(playerId: UUID, amount: Double, currencyId: Int, action: Action): Transaction {
    mySqlConnection.executeUpdateAndGetId(InsertTransaction(playerId, amount, currencyId, action)).let {
      return Transaction(it, playerId, currencyId, amount, action, Instant.now())
    }
  }

  override fun revokeTransaction(transactionId: Int) {
    mySqlConnection.executeUpdate(RemoveTransaction(transactionId))
  }

  override fun getLastTransactions(playerId: UUID, currencyId: Int, limit: Int): List<Transaction> {
    return mySqlConnection.executeQuery(SelectTransactionWithLimit(playerId, currencyId, limit))
  }

  override fun getTransaction(transactionId: Int): Transaction? {
    return mySqlConnection.executeQuery(SelectTransactionById(transactionId)).firstOrNull()
  }
}