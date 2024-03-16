package net.vellity.service.guardian

import net.vellity.service.guardian.report.Report
import net.vellity.service.guardian.report.ReportReason
import net.vellity.service.guardian.report.ReportState
import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ReportRequests {
  @GET("/reports/reasons")
  fun getReportReasons(
    @Query("context") context: Context
  ): Call<Collection<ReportReason>>

  @POST("/reports/reasons")
  fun createReportReason(
    @Query("context") context: Context,
    @Query("name") name: String
  ): Call<ReportReason>

  @DELETE("/reports/reasons")
  fun deleteReportReason(
    @Query("reason") reason: Int
  ): Call<Unit>

  @POST("/reports/active")
  fun createReport(
    @Query("context") context: Context,
    @Query("target") target: UUID,
    @Query("creator") creator: UUID,
    @Query("reason") reason: Int
  ): Call<Report>

  @PUT("/reports/active")
  fun updateReportStatus(
    @Query("report") report: Int,
    @Query("player") player: UUID,
    @Query("status") status: ReportState
  ): Call<Unit>

  @GET("/reports")
  fun getReportWithId(
    @Query("id") id: Int
  ): Call<Report>

  @GET("/reports/active")
  fun getActiveReports(
    @Query("context") context: Context
  ): Call<Collection<Report>>

  @GET("/reports/active/reason")
  fun getActiveReportsWithReason(
    @Query("context") context: Context,
    @Query("reason") reason: Int
  ): Call<Collection<Report>>

  @GET("/reports/active/status")
  fun getActiveReportsWithStatus(
    @Query("context") context: Context,
    @Query("status") status: ReportState
  ): Call<Collection<Report>>

  @GET("/reports/active/target")
  fun getActiveReportsWithTarget(
    @Query("context") context: Context,
    @Query("target") target: UUID
  ): Call<Collection<Report>>

  @GET("/reports/active/creator")
  fun getActiveReportsByCreator(
    @Query("context") context: Context,
    @Query("creator") creator: UUID
  ): Call<Collection<Report>>
}