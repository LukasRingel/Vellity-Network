package net.vellity.service.guardian.report

import net.vellity.utils.context.Context
import java.time.Instant

data class ReportReason(
  val id: Int,
  val context: Context,
  val name: String,
  val activeUntil: Instant
)