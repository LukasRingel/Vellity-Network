package net.vellity.service.guardian.report.mysql.action

import net.vellity.service.guardian.report.action.ReportActionType
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.*

class InsertAction(
  private val reportId: Int,
  private val player: UUID,
  private val action: ReportActionType
): UpdateQuery {
  override fun query(): String =
    "insert into  guardian_reports_actions(reportId, player, action) values(?, ?, ?);"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, reportId)
    preparedStatement.setUuid(2, player)
    preparedStatement.setEnum(3, action)
  }
}