package net.vellity.service.worker.history

import net.vellity.service.worker.WorkerRun
import net.vellity.service.worker.WorkerRunResult
import java.time.Instant

interface WorkerHistoryRepository {
  fun getRecentRunsByIdentifier(identifier: String, limit: Int): List<WorkerRun>

  fun createRun(worker: String, result: WorkerRunResult, startedAt: Instant, finishedAt: Instant): WorkerRun
}