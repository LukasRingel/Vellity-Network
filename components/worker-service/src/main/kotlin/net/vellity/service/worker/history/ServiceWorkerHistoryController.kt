package net.vellity.service.worker.history

import net.vellity.service.worker.WorkerRun
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceWorkerHistoryController(private val service: WorkerHistoryService) : WorkerHistoryController {
  override fun getRecentRunsByIdentifier(identifier: String, limit: Int): ResponseEntity<List<WorkerRun>> {
    return ResponseEntity.ok(service.getRecentRunsByIdentifier(identifier, limit))
  }
}