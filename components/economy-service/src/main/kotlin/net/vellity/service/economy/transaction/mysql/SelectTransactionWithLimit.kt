package net.vellity.service.economy.transaction.mysql

import net.vellity.service.economy.transaction.Action
import net.vellity.service.economy.transaction.Transaction
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.getUuid
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectTransactionWithLimit(
  private val playerId: UUID,
  private val currencyId: Int,
  private val limit: Int
) : SelectQuery<Transaction> {
  override fun query(): String {
    return "select * from economy_users_transactions where playerId = ? and currency = ? order by createdAt desc limit ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, playerId)
    preparedStatement.setInt(2, currencyId)
    preparedStatement.setInt(3, limit)
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