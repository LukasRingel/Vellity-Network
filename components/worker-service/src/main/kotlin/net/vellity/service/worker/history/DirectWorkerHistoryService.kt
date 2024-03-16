package net.vellity.service.worker.history

import net.vellity.service.worker.WorkerRun
import net.vellity.service.worker.WorkerRunResult
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class DirectWorkerHistoryService(
  private val repository: WorkerHistoryRepository
) : WorkerHistoryService {
  override fun getRecentRunsByIdentifier(identifier: String, limit: Int): List<WorkerRun> {
    return repository.getRecentRunsByIdentifier(identifier, limit)
  }

  override fun createRun(worker: String, result: WorkerRunResult, startedAt: Instant, finishedAt: Instant): WorkerRun {
    return repository.createRun(worker, result, startedAt, finishedAt)
  }
}