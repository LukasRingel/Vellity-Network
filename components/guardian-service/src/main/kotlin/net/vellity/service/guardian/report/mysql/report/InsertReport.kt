package net.vellity.service.guardian.report.mysql.report

import net.vellity.service.guardian.report.ReportState
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant
import java.util.*

class InsertReport(
  private val context: Context,
  private val target: UUID,
  private val creator: UUID,
  private val reason: Int,
  private val activeUntil: Instant
) : UpdateQuery {
  override fun query(): String =
    "insert into  guardian_reports(context, target, state, reason, creator, activeUntil) values(?,?,?,?,?,?);"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, target)
    preparedStatement.setEnum(3, ReportState.OPEN)
    preparedStatement.setInt(4, reason)
    preparedStatement.setUuid(5, creator)
    preparedStatement.setInstant(6, activeUntil)
  }
}