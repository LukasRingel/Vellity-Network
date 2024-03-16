package net.vellity.service.worker.api.rest

import net.vellity.service.worker.RestTriggeredWorkers
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceRestTriggeredController(private val service: RestTriggeredWorkerService) : RestTriggeredController {
  override fun triggerWorker(worker: RestTriggeredWorkers): ResponseEntity<Unit> {
    logger.info("Running rest worker $worker")
    service.triggerWorker(worker)
    return ResponseEntity.ok().build()
  }

  companion object {
    private val logger: Logger = LoggerFactory.getLogger(ServiceRestTriggeredController::class.java)
  }
}