package net.vellity.service.economy.currency.mysql

import net.vellity.service.economy.currency.Currency
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.query.SelectQuery
import net.vellity.utils.mysql.extensions.getContext
import net.vellity.utils.mysql.extensions.setContext
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectCurrenciesForContext(private val context: Context) : SelectQuery<Currency> {
  override fun query(): String {
    return "SELECT * FROM economy_currencies WHERE context = ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
  }

  override fun mapper(resultSet: ResultSet): Currency {
    return Currency(
      resultSet.getInt("id"),
      resultSet.getContext("context"),
      resultSet.getString("name"),
      resultSet.getDouble("defaultBalance"),
      mutableListOf()
    )
  }
}