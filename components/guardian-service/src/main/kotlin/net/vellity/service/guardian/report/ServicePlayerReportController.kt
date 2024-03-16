package net.vellity.service.guardian.report

import net.vellity.service.guardian.report.exception.UnknownReportException
import net.vellity.service.guardian.report.exception.UnknownReportReasonException
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ServicePlayerReportController(private val service: PlayerReportService) : PlayerReportController {
  override fun getReportReasons(context: Context): ResponseEntity<Collection<ReportReason>> {
    return ResponseEntity.ok(service.getReportReasons(context))
  }

  override fun createReportReason(context: Context, name: String): ResponseEntity<ReportReason> {
    return ResponseEntity.ok(service.createReportReason(context, name))
  }

  override fun deleteReportReason(reason: Int): ResponseEntity<Unit> {
    service.deleteReportReason(reason)
    return ResponseEntity.ok().build()
  }

  override fun createReport(context: Context, target: UUID, creator: UUID, reason: Int): ResponseEntity<Report> {
    val reportReason = service.getReportReasonById(context, reason)?: throw UnknownReportReasonException()
    return ResponseEntity.ok(service.createReport(context, target, creator, reportReason))
  }

  override fun updateReportStatus(report: Int, player: UUID, status: ReportState): ResponseEntity<Unit> {
    val reportObject = service.getReportById(report)?: throw UnknownReportException()
    service.updateReportStatus(reportObject, player, status)
    return ResponseEntity.ok().build()
  }

  override fun getReportWithId(id: Int): ResponseEntity<Report> {
    val reportObject = service.getReportById(id)?: throw UnknownReportException()
    return ResponseEntity.ok(reportObject)
  }

  override fun getActiveReports(context: Context): ResponseEntity<Collection<Report>> {
    return ResponseEntity.ok(service.getActiveReports(context))
  }

  override fun getActiveReportsWithReason(context: Context, reason: Int): ResponseEntity<Collection<Report>> {
    val reportReason = service.getReportReasonById(context, reason)?: throw UnknownReportReasonException()
    return ResponseEntity.ok(service.getActiveReportsWithReason(context, reportReason))
  }

  override fun getActiveReportsWithStatus(context: Context, status: ReportState): ResponseEntity<Collection<Report>> {
    return ResponseEntity.ok(service.getActiveReportsWithStatus(context, status))
  }

  override fun getActiveReportsWithTarget(context: Context, target: UUID): ResponseEntity<Collection<Report>> {
    return ResponseEntity.ok(service.getActiveReportsWithTarget(context, target))
  }

  override fun getActiveReportsByCreator(context: Context, creator: UUID): ResponseEntity<Collection<Report>> {
    return ResponseEntity.ok(service.getActiveReportsByCreator(context, creator))
  }
}