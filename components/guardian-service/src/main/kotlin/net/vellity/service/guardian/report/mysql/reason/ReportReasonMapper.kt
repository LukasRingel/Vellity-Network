package net.vellity.service.guardian.report.mysql.reason

import net.vellity.service.guardian.report.ReportReason
import net.vellity.utils.mysql.extensions.getContext
import net.vellity.utils.mysql.extensions.getInstant
import java.sql.ResultSet

class ReportReasonMapper {
  companion object {
    fun resultSetToModel(resultSet: ResultSet): ReportReason {
      return ReportReason(
        resultSet.getInt("id"),
        resultSet.getContext("context"),
        resultSet.getString("name"),
        resultSet.getInstant("activeUntil")
      )
    }

    fun resultSetToModelForJoinedQueries(resultSet: ResultSet): ReportReason {
      return ReportReason(
        resultSet.getInt("reasonId"),
        resultSet.getContext("context"),
        resultSet.getString("reasonName"),
        resultSet.getInstant("reasonActiveUntil")
      )
    }
  }
}