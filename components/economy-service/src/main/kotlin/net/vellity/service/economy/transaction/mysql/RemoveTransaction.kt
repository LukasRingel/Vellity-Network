package net.vellity.service.economy.transaction.mysql

import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class RemoveTransaction(private val transactionId: Int) : UpdateQuery {
  override fun query(): String {
    return "delete from economy_users_transactions where id = ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, transactionId)
  }
}