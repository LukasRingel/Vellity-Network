package net.vellity.service.economy.currency.mysql

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.query.UpdateQuery
import net.vellity.utils.mysql.extensions.setContext
import java.sql.PreparedStatement

class InsertCurrency(
  private val context: Context,
  private val name: String,
  private val defaultValue: Double
) : UpdateQuery {
  override fun query(): String {
    return "INSERT INTO economy_currencies (context, name, defaultBalance) VALUES (?, ?, ?)"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setString(2, name)
    preparedStatement.setDouble(3, defaultValue)
  }
}