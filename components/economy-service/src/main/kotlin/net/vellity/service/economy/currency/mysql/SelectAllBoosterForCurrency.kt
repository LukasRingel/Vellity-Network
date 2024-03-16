package net.vellity.service.economy.currency.mysql

import net.vellity.service.economy.currency.CurrencyBooster
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectAllBoosterForCurrency(private val currencyId: Int) : SelectQuery<CurrencyBooster> {
  override fun query(): String {
    return "select * from economy_currencies_booster where currencyId = ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, currencyId)
  }

  override fun mapper(resultSet: ResultSet): CurrencyBooster {
    return CurrencyBooster(
      resultSet.getInt("id"),
      resultSet.getInt("currencyId"),
      resultSet.getDouble("amount"),
      resultSet.getInstant("startAt"),
      resultSet.getInstant("activeUntil")
    )
  }
}