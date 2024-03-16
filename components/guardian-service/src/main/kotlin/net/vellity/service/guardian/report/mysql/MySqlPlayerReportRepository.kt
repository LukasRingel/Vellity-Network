package net.vellity.service.guardian.report.mysql

import net.vellity.service.guardian.report.PlayerReportRepository
import net.vellity.service.guardian.report.Report
import net.vellity.service.guardian.report.ReportReason
import net.vellity.service.guardian.report.ReportState
import net.vellity.service.guardian.report.action.ReportActionType
import net.vellity.service.guardian.report.mysql.action.BulkSelectActionsForReports
import net.vellity.service.guardian.report.mysql.action.InsertAction
import net.vellity.service.guardian.report.mysql.reason.DeleteReason
import net.vellity.service.guardian.report.mysql.reason.InsertReason
import net.vellity.service.guardian.report.mysql.reason.SelectAllReportReasons
import net.vellity.service.guardian.report.mysql.report.InsertReport
import net.vellity.service.guardian.report.mysql.report.SelectActiveReportsInContext
import net.vellity.service.guardian.report.mysql.report.SelectReportWithId
import net.vellity.service.guardian.report.mysql.report.UpdateStateOfReport
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.configuration.lifetimeDate
import net.vellity.utils.mysql.extensions.nowUtc
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class MySqlPlayerReportRepository(private val mySqlConnection: MySqlConnection) : PlayerReportRepository {
  override fun getReportReasons(): List<ReportReason> {
    return mySqlConnection.executeQuery(SelectAllReportReasons())
  }

  override fun createReportReason(context: Context, name: String): ReportReason {
    val id = mySqlConnection.executeUpdateAndGetId(InsertReason(context, name, lifetimeDate))
    return ReportReason(id, context, name, lifetimeDate)
  }

  override fun deleteReportReason(reason: Int) {
    mySqlConnection.executeUpdate(DeleteReason(reason))
  }

  override fun getReportById(id: Int): Report? {
    return mySqlConnection.executeQuery(SelectReportWithId(id)).firstOrNull()
  }

  override fun getActiveReports(context: Context): List<Report> {
    val reports = mySqlConnection.executeQuery(SelectActiveReportsInContext(context))
    val actions = mySqlConnection.executeQuery(BulkSelectActionsForReports(reports.map { it.id }))
    reports.forEach { report ->
      report.actions.addAll(actions.filter { it.reportId == report.id })
    }
    return reports
  }

  override fun createReport(
    context: Context,
    target: UUID,
    creator: UUID,
    reason: ReportReason,
    activeUntil: Instant
  ): Report {
    val id = mySqlConnection.executeUpdateAndGetId(InsertReport(context, target, creator, reason.id, activeUntil))
    return Report(
      id,
      context,
      target,
      creator,
      ReportState.OPEN,
      reason,
      mutableListOf(),
      activeUntil,
      nowUtc()
    )
  }

  override fun updateReportStatus(report: Report, status: ReportState) {
    mySqlConnection.executeUpdate(UpdateStateOfReport(report.id, status))
  }

  override fun insertAction(reportId: Int, player: UUID, action: ReportActionType) {
    mySqlConnection.executeUpdate(InsertAction(reportId, player, action))
  }
}