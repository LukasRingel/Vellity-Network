package net.vellity.service.guardian.report.mysql.action

import net.vellity.service.guardian.report.action.ReportAction
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectActionsForReport(private val reportId: Int) : SelectQuery<ReportAction> {
  override fun query(): String =
    "select * from  guardian_reports_actions where reportId = ? order by createdAt desc"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, reportId)
  }

  override fun mapper(resultSet: ResultSet): ReportAction {
    return ReportActionMapper.resultSetToAction(resultSet)
  }
}