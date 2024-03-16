package net.vellity.service.guardian.report.mysql.reason

import net.vellity.service.guardian.report.ReportReason
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectAllReportReasons : SelectQuery<ReportReason> {
  override fun query(): String {
    return "SELECT * FROM  guardian_reports_reasons where activeUntil > now()"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    // Nothing to replace
  }

  override fun mapper(resultSet: ResultSet): ReportReason {
    return ReportReasonMapper.resultSetToModel(resultSet)
  }
}