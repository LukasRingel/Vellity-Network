package net.vellity.service.guardian.report

import net.vellity.service.guardian.report.action.ReportActionType
import net.vellity.utils.context.Context
import java.util.UUID

interface PlayerReportService {
  fun getReportReasonById(context: Context, id: Int): ReportReason?

  fun getReportById(id: Int): Report?

  fun getReportReasons(context: Context): Collection<ReportReason>

  fun createReportReason(context: Context, name: String): ReportReason

  fun deleteReportReason(reason: Int)

  fun getActiveReports(context: Context): Collection<Report>

  fun getActiveReportsWithReason(context: Context, reason: ReportReason): Collection<Report>

  fun getActiveReportsWithStatus(context: Context, status: ReportState): Collection<Report>

  fun getActiveReportsWithTarget(context: Context, target: UUID): Collection<Report>

  fun getActiveReportsByCreator(context: Context, creator: UUID): Collection<Report>

  fun createReport(context: Context, target: UUID, creator: UUID, reason: ReportReason): Report

  fun updateReportStatus(report: Report, player: UUID, status: ReportState)

  fun createAction(report: Report, player: UUID, action: ReportActionType)
}