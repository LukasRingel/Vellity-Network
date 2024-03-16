package net.vellity.service.economy.currency.mysql

import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant

class InsertCurrencyBooster(
  private val currencyId: Int,
  private val amount: Double,
  private val startAt: Instant,
  private val activeUntil: Instant
) : UpdateQuery {
  override fun query(): String {
    return "insert into economy_currencies_booster (currencyId, amount, startAt, activeUntil) values (?, ?, ?, ?)"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, currencyId)
    preparedStatement.setDouble(2, amount)
    preparedStatement.setInstant(3, startAt)
    preparedStatement.setInstant(4, activeUntil)
  }
}