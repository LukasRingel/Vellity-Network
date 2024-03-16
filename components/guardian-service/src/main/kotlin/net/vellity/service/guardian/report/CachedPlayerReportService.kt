package net.vellity.service.guardian.report

import net.vellity.service.guardian.GuardianServiceConfiguration
import net.vellity.service.guardian.report.action.ReportActionType
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.nowUtc
import net.vellity.utils.redis.RedisConnection
import net.vellity.utils.redis.RedisSynchronizer
import org.springframework.stereotype.Service
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class CachedPlayerReportService(
  redisConnection: RedisConnection,
  private val configuration: GuardianServiceConfiguration,
  private val repository: PlayerReportRepository
) : PlayerReportService {

  private var reportReasons: List<ReportReason> = repository.getReportReasons()

  private val synchronizer = RedisSynchronizer(redisConnection, "guardian:reasons:report") {
    reportReasons = repository.getReportReasons()
  }

  override fun getReportReasonById(context: Context, id: Int): ReportReason? {
    return reportReasons.firstOrNull { it.context == context && it.id == id }
  }

  override fun getReportById(id: Int): Report? {
    return repository.getReportById(id)
  }

  override fun getReportReasons(context: Context): Collection<ReportReason> {
    return reportReasons.filter { it.context == context }
  }

  override fun createReportReason(context: Context, name: String): ReportReason {
    return repository.createReportReason(context, name).also {
      synchronizer.deployUpdate()
    }
  }

  override fun deleteReportReason(reason: Int) {
    repository.deleteReportReason(reason).also {
      synchronizer.deployUpdate()
    }
  }

  override fun getActiveReports(context: Context): Collection<Report> {
    return repository.getActiveReports(context)
  }

  override fun getActiveReportsWithReason(context: Context, reason: ReportReason): Collection<Report> {
    return getActiveReports(context).filter { it.reason == reason }
  }

  override fun getActiveReportsWithStatus(context: Context, status: ReportState): Collection<Report> {
    return getActiveReports(context).filter { it.state == status }
  }

  override fun getActiveReportsWithTarget(context: Context, target: UUID): Collection<Report> {
    return getActiveReports(context).filter { it.target == target }
  }

  override fun getActiveReportsByCreator(context: Context, creator: UUID): Collection<Report> {
    return getActiveReports(context).filter { it.creator == creator }
  }

  override fun createReport(context: Context, target: UUID, creator: UUID, reason: ReportReason): Report {
    return repository.createReport(
      context,
      target,
      creator,
      reason,
      nowUtc().plus(configuration.reportLifetime(), ChronoUnit.MINUTES)
    )
  }

  override fun updateReportStatus(report: Report, player: UUID, status: ReportState) {
    repository.updateReportStatus(report, status)
    createAction(report, player, ReportActionType.forUpdatedState(status))
  }

  override fun createAction(report: Report, player: UUID, action: ReportActionType) {
    repository.insertAction(report.id, player, action)
  }
}