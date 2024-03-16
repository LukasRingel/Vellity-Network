package net.vellity.service.economy.user.mysql

import net.vellity.service.economy.user.UserBalance
import net.vellity.utils.mysql.extensions.getUuid
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectBalanceForCurrency(private val player: UUID, private val currencyId: Int) : SelectQuery<UserBalance> {
  override fun query(): String {
    return "select * from economy_users_balance where playerId = ? and currency = ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, player)
    preparedStatement.setInt(2, currencyId)
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