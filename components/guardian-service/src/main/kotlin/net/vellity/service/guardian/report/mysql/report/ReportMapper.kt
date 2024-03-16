package net.vellity.service.guardian.report.mysql.report

import net.vellity.service.guardian.report.Report
import net.vellity.service.guardian.report.ReportState
import net.vellity.service.guardian.report.mysql.reason.ReportReasonMapper
import net.vellity.utils.mysql.extensions.getContext
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.getUuid
import java.sql.ResultSet

class ReportMapper {
  companion object {
    fun resultSetToModel(resultSet: ResultSet): Report {
      return Report(
        resultSet.getInt("id"),
        resultSet.getContext("context"),
        resultSet.getUuid("target"),
        resultSet.getUuid("creator"),
        ReportState.fromId(resultSet.getInt("state")),
        ReportReasonMapper.resultSetToModelForJoinedQueries(resultSet),
        mutableListOf(),
        resultSet.getInstant("activeUntil"),
        resultSet.getInstant("createdAt")
      )
    }
  }
}