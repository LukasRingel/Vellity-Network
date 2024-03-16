package net.vellity.service.economy.user.mysql

import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.UUID

class UpdateBalanceForCurrency(private val playerId: UUID, private val currencyId: Int, private val amount: Double, private val default: Double) : UpdateQuery {
  override fun query(): String {
    return "insert into economy_users_balance(playerId, currency, balance) values (?,?,?) on duplicate key update balance = balance + ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, playerId)
    preparedStatement.setInt(2, currencyId)
    preparedStatement.setDouble(3, default + amount)
    preparedStatement.setDouble(4, amount)
  }
}