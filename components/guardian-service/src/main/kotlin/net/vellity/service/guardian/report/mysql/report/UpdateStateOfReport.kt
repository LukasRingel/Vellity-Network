package net.vellity.service.guardian.report.mysql.report

import net.vellity.service.guardian.report.ReportState
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class UpdateStateOfReport(private val reportId: Int, private val state: ReportState): UpdateQuery {
  override fun query(): String = "update  guardian_reports set state = ? where id = ?"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setEnum(1, state)
    preparedStatement.setInt(2, reportId)
  }
}