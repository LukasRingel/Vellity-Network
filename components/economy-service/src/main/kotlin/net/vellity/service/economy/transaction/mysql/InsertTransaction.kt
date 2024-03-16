package net.vellity.service.economy.transaction.mysql

import net.vellity.service.economy.transaction.Action
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.*

class InsertTransaction(
  private val playerId: UUID,
  private val amount: Double,
  private val currencyId: Int,
  private val action: Action
) : UpdateQuery {
  override fun query(): String {
    return "insert into economy_users_transactions(playerId, currency, amount, action) values (?,?,?,?)"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, playerId)
    preparedStatement.setInt(2, currencyId)
    preparedStatement.setDouble(3, amount)
    preparedStatement.setEnum(4, action)
  }
}