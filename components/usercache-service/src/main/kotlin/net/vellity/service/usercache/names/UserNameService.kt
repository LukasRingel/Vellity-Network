package net.vellity.service.usercache.names

import net.vellity.service.usercache.name.NameHistoryEntry
import java.util.*

interface UserNameService {
  fun getNameByUuid(player: UUID): String

  fun getNameHistory(player: UUID): List<NameHistoryEntry>

  fun getNamesByUuids(players: List<UUID>): Map<UUID, String>

  fun checkForNameChange(player: UUID, name: String)

  fun getUuidByName(name: String): UUID
}