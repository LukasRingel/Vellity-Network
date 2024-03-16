package net.vellity.service.guardian.report.mysql.action

import net.vellity.service.guardian.report.action.ReportAction
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class BulkSelectActionsForReports(private val reportIds: List<Int>) : SelectQuery<ReportAction> {
  override fun query(): String =
    "select * from  guardian_reports_actions where reportId in ("+
      reportIds.joinToString(",") { "?" } + ");"

  override fun replace(preparedStatement: PreparedStatement) {
    reportIds.forEachIndexed { index, reportId ->
      preparedStatement.setInt(index + 1, reportId)
    }
  }

  override fun mapper(resultSet: ResultSet): ReportAction {
    return ReportActionMapper.resultSetToAction(resultSet)
  }
}