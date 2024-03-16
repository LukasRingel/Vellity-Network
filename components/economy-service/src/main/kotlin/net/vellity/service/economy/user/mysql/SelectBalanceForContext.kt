package net.vellity.service.economy.user.mysql

import net.vellity.service.economy.user.UserBalance
import net.vellity.utils.mysql.extensions.getUuid
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectBalanceForContext(private val playerId: UUID, private val contextId: Int) : SelectQuery<UserBalance> {
  override fun query(): String {
    return "with currencyInContext as (select * from economy_currencies where context = ?) " +
      "select currencyInContext.id as currency, " +
      "balance.id as id, " +
      "balance.playerId as playerId, " +
      "balance.balance as balance " +
      "from currencyInContext, economy_users_balance balance " +
      "where balance.currency = currencyInContext.id " +
      "and balance.playerId = ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, contextId)
    preparedStatement.setUuid(2, playerId)
  }

  override fun mapper(resultSet: ResultSet): UserBalance {
    return UserBalance(
      resultSet.getInt("id"),
      resultSet.getUuid("playerId"),
      resultSet.getDouble("balance"),
      resultSet.getInt("currency")
    )
  }
}