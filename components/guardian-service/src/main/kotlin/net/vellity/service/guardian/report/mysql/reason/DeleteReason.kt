package net.vellity.service.guardian.report.mysql.reason

import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class DeleteReason(private val id: Int): UpdateQuery {
  override fun query(): String =
    "update  guardian_reports_reasons set activeUntil = current_timestamp where id = ?"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, id)
  }
}