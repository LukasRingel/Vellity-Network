package net.vellity.service.worker

import java.time.Instant

data class WorkerRun(
  val id: Int,
  val worker: String,
  val startedAt: Instant,
  val finishedAt: Instant,
  val result: WorkerRunResult
)