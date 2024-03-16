package net.vellity.service.guardian.report

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

interface PlayerReportController {
  @GetMapping("/reports/reasons")
  fun getReportReasons(
    @RequestParam("context") context: Context
  ): ResponseEntity<Collection<ReportReason>>

  @PostMapping("/reports/reasons")
  fun createReportReason(
    @RequestParam("context") context: Context,
    @RequestParam("name") name: String
  ): ResponseEntity<ReportReason>

  @DeleteMapping("/reports/reasons")
  fun deleteReportReason(
    @RequestParam("reason") reason: Int
  ): ResponseEntity<Unit>

  @PostMapping("/reports/active")
  fun createReport(
    @RequestParam("context") context: Context,
    @RequestParam("target") target: UUID,
    @RequestParam("creator") creator: UUID,
    @RequestParam("reason") reason: Int
  ): ResponseEntity<Report>

  @PutMapping("/reports/active")
  fun updateReportStatus(
    @RequestParam("report") report: Int,
    @RequestParam("player") player: UUID,
    @RequestParam("status") status: ReportState
  ): ResponseEntity<Unit>

  @GetMapping("/reports")
  fun getReportWithId(
    @RequestParam("id") id: Int
  ): ResponseEntity<Report>

  @GetMapping("/reports/active")
  fun getActiveReports(
    @RequestParam("context") context: Context
  ): ResponseEntity<Collection<Report>>

  @GetMapping("/reports/active/reason")
  fun getActiveReportsWithReason(
    @RequestParam("context") context: Context,
    @RequestParam("reason") reason: Int
  ): ResponseEntity<Collection<Report>>

  @GetMapping("/reports/active/status")
  fun getActiveReportsWithStatus(
    @RequestParam("context") context: Context,
    @RequestParam("status") status: ReportState
  ): ResponseEntity<Collection<Report>>

  @GetMapping("/reports/active/target")
  fun getActiveReportsWithTarget(
    @RequestParam("context") context: Context,
    @RequestParam("target") target: UUID
  ): ResponseEntity<Collection<Report>>

  @GetMapping("/reports/active/creator")
  fun getActiveReportsByCreator(
    @RequestParam("context") context: Context,
    @RequestParam("creator") creator: UUID
  ): ResponseEntity<Collection<Report>>
}