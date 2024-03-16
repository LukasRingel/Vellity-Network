package net.vellity.service.economy.user.mysql

import net.vellity.service.economy.user.UserBooster
import net.vellity.utils.mysql.extensions.*
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectBoosterForPlayerForCurrency(
  private val playerId: UUID,
  private val currencyId: Int
) : SelectQuery<UserBooster> {
  override fun query(): String =
    "select * from economy_user_booster where " +
      "playerId = ? and currencyId = ? and activeUntil > now()"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, playerId)
    preparedStatement.setInt(2, currencyId)
  }

  override fun mapper(resultSet: ResultSet): UserBooster =
    UserBooster(
      resultSet.getInt("id"),
      resultSet.getUuid("playerId"),
      resultSet.getInt("currencyId"),
      resultSet.getDouble("amount"),
      resultSet.getInstant("activeUntil")
    )
}