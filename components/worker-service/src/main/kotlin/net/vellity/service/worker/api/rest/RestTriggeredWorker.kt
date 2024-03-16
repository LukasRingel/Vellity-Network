package net.vellity.service.worker.api.rest

import net.vellity.service.worker.RestTriggeredWorkers
import net.vellity.service.worker.api.Worker

abstract class RestTriggeredWorker(private val worker: RestTriggeredWorkers): Worker {
  override fun identifier(): String = worker.name

  abstract fun run()
}