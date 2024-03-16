package net.vellity.service.economy.transaction.mysql

import net.vellity.service.economy.transaction.Action
import net.vellity.service.economy.transaction.Transaction
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.getUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectTransactionById(private val transactionId: Int): SelectQuery<Transaction> {
  override fun query(): String {
    return "SELECT * FROM economy_users_transactions WHERE id = ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, transactionId)
  }

  override fun mapper(resultSet: ResultSet): Transaction {
    return Transaction(
      resultSet.getInt("id"),
      resultSet.getUuid("playerId"),
      resultSet.getInt("currency"),
      resultSet.getDouble("amount"),
      Action.values()[resultSet.getInt("action")],
      resultSet.getInstant("createdAt")
    )
  }
}