package net.vellity.service.guardian.report

import net.vellity.service.guardian.report.action.ReportActionType
import net.vellity.utils.context.Context
import java.time.Instant
import java.util.UUID

interface PlayerReportRepository {

  fun getReportReasons(): List<ReportReason>

  fun createReportReason(context: Context, name: String): ReportReason

  fun deleteReportReason(reason: Int)

  fun getReportById(id: Int): Report?

  fun getActiveReports(context: Context): List<Report>

  fun createReport(context: Context, target: UUID, creator: UUID, reason: ReportReason, activeUntil: Instant): Report

  fun updateReportStatus(report: Report, status: ReportState)

  fun insertAction(reportId: Int, player: UUID, action: ReportActionType)
}