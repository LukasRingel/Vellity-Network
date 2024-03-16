package net.vellity.service.guardian.report.action

import java.time.Instant
import java.util.*

data class ReportAction(
  val id: Int,
  val reportId: Int,
  val player: UUID,
  val action: ReportActionType,
  val createdAt: Instant
)