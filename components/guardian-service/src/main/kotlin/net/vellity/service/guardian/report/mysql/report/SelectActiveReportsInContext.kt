package net.vellity.service.guardian.report.mysql.report

import net.vellity.service.guardian.report.Report
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectActiveReportsInContext(private val context: Context) : SelectQuery<Report> {
  override fun query(): String =
    "select report.id as id, " +
      "       report.context as context, " +
      "       report.target as target, " +
      "       report.state as state, " +
      "       report.reason as reason, " +
      "       report.creator as creator, " +
      "       report.activeUntil as activeUntil, " +
      "       report.createdAt as createdAt, " +
      "       reason.id as reasonId, " +
      "       reason.name as reasonName, " +
      "       reason.activeUntil as reasonActiveUntil " +
      "from  guardian_reports report " +
      "         left join  guardian_reports_reasons reason on reason.id = report.reason " +
      "where report.context = ? and report.activeUntil > now()"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
  }

  override fun mapper(resultSet: ResultSet): Report {
    return ReportMapper.resultSetToModel(resultSet)
  }
}