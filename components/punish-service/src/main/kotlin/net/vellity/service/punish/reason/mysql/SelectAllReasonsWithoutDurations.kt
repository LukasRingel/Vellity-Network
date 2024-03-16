package net.vellity.service.punish.reason.mysql

import net.vellity.service.punish.reason.Reason
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectAllReasonsWithoutDurations : SelectQuery<Reason> {
  override fun query(): String =
    "select * from punish_reasons where activeUntil > now();"

  override fun replace(preparedStatement: PreparedStatement) {
    // Nothing to replace
  }

  override fun mapper(resultSet: ResultSet): Reason =
    ReasonResultSetMapper.resultSetToReason(resultSet)
}