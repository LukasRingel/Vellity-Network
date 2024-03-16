package net.vellity.service.usercache.names

import net.vellity.service.usercache.name.NameHistoryEntry
import net.vellity.service.usercache.name.NameResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

interface UserNameController {
  @GetMapping("/names/current")
  fun getNameByUuid(@RequestParam("player") player: UUID): ResponseEntity<NameResponse>

  @GetMapping("/names/history")
  fun getNameHistory(@RequestParam("player") player: UUID): ResponseEntity<List<NameHistoryEntry>>

  @PutMapping("/names/current/bulk")
  fun getNamesByUuids(@RequestBody players: List<String>): ResponseEntity<List<NameResponse>>

  @GetMapping("/names/current/uuid")
  fun getUuidByName(@RequestParam("name") name: String): ResponseEntity<UUID>

  @PostMapping("/names/check")
  fun checkForNameChange(@RequestParam("player") player: UUID, @RequestParam("name") name: String): ResponseEntity<Unit>
}