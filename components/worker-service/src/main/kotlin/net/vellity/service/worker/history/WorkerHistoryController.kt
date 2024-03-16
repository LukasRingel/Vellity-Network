package net.vellity.service.worker.history

import net.vellity.service.worker.WorkerRun
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

interface WorkerHistoryController {
  @GetMapping("/history")
  fun getRecentRunsByIdentifier(
    @RequestParam("identifier") identifier: String,
    @RequestParam(name = "limit", defaultValue = "50") limit: Int
  ): ResponseEntity<List<WorkerRun>>
}