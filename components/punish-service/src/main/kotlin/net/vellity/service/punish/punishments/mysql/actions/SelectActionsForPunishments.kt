package net.vellity.service.punish.punishments.mysql.actions

import net.vellity.service.punish.punishments.PunishmentAction
import net.vellity.service.punish.punishments.mysql.PunishmentResultSetMapper
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectActionsForPunishments(private val punishments: List<Int>) : SelectQuery<PunishmentAction> {
  override fun query(): String =
    "select * from punish_users_punishments_actions " +
      "where punishment in (" + punishments.joinToString(",") { "?" } + ");"

  override fun replace(preparedStatement: PreparedStatement) {
    punishments.forEachIndexed { index, punishment ->
      preparedStatement.setInt(index + 1, punishment)
    }
  }

  override fun mapper(resultSet: ResultSet): PunishmentAction =
    PunishmentResultSetMapper.resultSetToPunishmentAction(resultSet)
}