package net.vellity.service.punish.reason.mysql.duration

import net.vellity.service.punish.reason.ReasonDuration
import net.vellity.service.punish.reason.mysql.ReasonResultSetMapper
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectDurationsForReports(private val ids: List<Int>) : SelectQuery<ReasonDuration> {
  override fun query(): String =
    "select * from punish_reasons_durations where reason " +
      "in (" + ids.joinToString(",") { "?" } + ") " +
      "and activeUntil > now();"

  override fun replace(preparedStatement: PreparedStatement) {
    ids.forEachIndexed { index, id ->
      preparedStatement.setInt(index + 1, id)
    }
  }

  override fun mapper(resultSet: ResultSet): ReasonDuration =
    ReasonResultSetMapper.resultSetToDuration(resultSet)
}