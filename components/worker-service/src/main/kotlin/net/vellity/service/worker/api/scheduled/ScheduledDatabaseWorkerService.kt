package net.vellity.service.worker.api.scheduled

import net.vellity.service.worker.WorkerRunResult
import net.vellity.service.worker.history.EnableHistory
import net.vellity.service.worker.history.WorkerHistoryService
import net.vellity.utils.mysql.extensions.nowUtc
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import java.util.concurrent.Executors

@Service
class ScheduledDatabaseWorkerService(
  applicationContext: ApplicationContext,
  private val workerHistoryService: WorkerHistoryService
) {
  init {
    val workers = applicationContext.getBeansOfType(ScheduledWorker::class.java)
    val executorService = Executors.newScheduledThreadPool(workers.size)
    workers.forEach { (_, worker) ->
      executorService.scheduleAtFixedRate(
        {
          logger.info("Running scheduled worker ${worker.identifier()}")
          val startedAt = nowUtc()

          val result = try {
            worker.run()
            WorkerRunResult.SUCCESS
          } catch (e: Exception) {
            WorkerRunResult.FAILED
          }

          if (worker::class.java.isAnnotationPresent(EnableHistory::class.java)) {
            val finishedAt = nowUtc()
            workerHistoryService.createRun(
              worker::class.java.getDeclaredAnnotation(EnableHistory::class.java).value,
              result,
              startedAt,
              finishedAt
            )
          }

        },
        0,
        worker.timeUnitAmount,
        worker.timeUnit
      )
    }
  }

  companion object {
    private val logger: Logger = LoggerFactory.getLogger(ScheduledDatabaseWorkerService::class.java)
  }
}