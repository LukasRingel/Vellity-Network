package net.vellity.service.worker.api.rest

import net.vellity.service.worker.RestTriggeredWorkers
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

interface RestTriggeredController {
  @PostMapping("/trigger")
  fun triggerWorker(@RequestParam("worker") worker: RestTriggeredWorkers): ResponseEntity<Unit>
}