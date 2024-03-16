package net.vellity.service.usercache.names

import net.vellity.service.usercache.name.NameHistoryEntry
import java.util.UUID

interface UserNameRepository {
  fun getNameByUuid(player: UUID): String

  fun getNameHistory(player: UUID): List<NameHistoryEntry>

  fun getNamesByUuids(players: List<UUID>): List<Pair<UUID, String>>

  fun insertOrUpdateName(player: UUID, name: String)

  fun insertHistoryEntry(player: UUID, name: String)

  fun getUuidByName(name: String): UUID?
}