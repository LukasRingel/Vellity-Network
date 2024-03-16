package net.vellity.service.guardian.report

import net.vellity.service.guardian.report.action.ReportAction
import net.vellity.utils.context.Context
import java.time.Instant
import java.util.UUID

data class Report(
  val id: Int,
  val context: Context,
  val target: UUID,
  val creator: UUID,
  val state: ReportState,
  val reason: ReportReason,
  var actions: MutableList<ReportAction>,
  val activeUntil: Instant,
  val createdAt: Instant
)
