package net.vellity.service.guardian.report.mysql.reason

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant

class InsertReason(private val context: Context, private val name: String, private val activeUntil: Instant): UpdateQuery {
  override fun query(): String {
    return "INSERT INTO  guardian_reports_reasons (context, name, activeUntil) VALUES (?, ?, ?)"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setString(2, name)
    preparedStatement.setInstant(3, activeUntil)
  }
}