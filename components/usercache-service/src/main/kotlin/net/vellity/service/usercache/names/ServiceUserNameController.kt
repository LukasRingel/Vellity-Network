package net.vellity.service.usercache.names

import net.vellity.service.usercache.name.NameHistoryEntry
import net.vellity.service.usercache.name.NameResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ServiceUserNameController(private val userNameService: UserNameService): UserNameController {
  override fun getNameByUuid(player: UUID): ResponseEntity<NameResponse> {
    return ResponseEntity.ok(NameResponse(player, userNameService.getNameByUuid(player)))
  }

  override fun getNameHistory(player: UUID): ResponseEntity<List<NameHistoryEntry>> {
    return ResponseEntity.ok(userNameService.getNameHistory(player))
  }

  override fun getNamesByUuids(players: List<String>): ResponseEntity<List<NameResponse>> {
    try {
      val namesByUuids = userNameService.getNamesByUuids(players.map { UUID.fromString(it) })
      return ResponseEntity.ok(namesByUuids.map { NameResponse(it.key, it.value) })
    } catch (e: Exception) {
      e.printStackTrace()
      return ResponseEntity.ok(emptyList())
    }
  }

  override fun getUuidByName(name: String): ResponseEntity<UUID> {
    return ResponseEntity.ok(userNameService.getUuidByName(name))
  }

  override fun checkForNameChange(player: UUID, name: String): ResponseEntity<Unit> {
    userNameService.checkForNameChange(player, name)
    return ResponseEntity.ok().build()
  }
}