package net.vellity.service.economy.currency.mysql

import net.vellity.service.economy.currency.Currency
import net.vellity.utils.mysql.query.SelectQuery
import net.vellity.utils.mysql.extensions.getContext
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectAllCurrencies : SelectQuery<Currency> {
  override fun query(): String {
    return "SELECT * FROM economy_currencies"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    // Nothing to replace
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