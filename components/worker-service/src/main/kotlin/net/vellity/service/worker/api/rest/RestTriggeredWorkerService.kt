package net.vellity.service.worker.api.rest

import net.vellity.service.worker.RestTriggeredWorkers

interface RestTriggeredWorkerService {
  fun triggerWorker(worker: RestTriggeredWorkers)
}