package net.vellity.service.worker.api.rest

import net.vellity.service.worker.RestTriggeredWorkers
import net.vellity.service.worker.WorkerRunResult
import net.vellity.service.worker.history.EnableHistory
import net.vellity.service.worker.history.WorkerHistoryService
import net.vellity.utils.mysql.extensions.nowUtc
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Service
class SpringRestTriggeredWorkerService(
  applicationContext: ApplicationContext,
  private val workerHistoryService: WorkerHistoryService
) : RestTriggeredWorkerService {
  private val worker = mutableMapOf<RestTriggeredWorkers, RestTriggeredWorker>()

  init {
    applicationContext.getBeansOfType(RestTriggeredWorker::class.java).forEach { (_, worker) ->
      this.worker[RestTriggeredWorkers.valueOf(worker.identifier())] = worker
    }
  }

  override fun triggerWorker(worker: RestTriggeredWorkers) {
    val startedAt = nowUtc()
    val result = try {
      this.worker[worker]?.run()
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
  }
}