package net.vellity.service.usercache.names

import net.vellity.service.usercache.name.NameHistoryEntry
import net.vellity.service.usercache.name.UuidNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class DirectUserNameService(private val userNameRepository: UserNameRepository) : UserNameService {
  override fun getNameByUuid(player: UUID): String {
    return userNameRepository.getNameByUuid(player)
  }

  override fun getNameHistory(player: UUID): List<NameHistoryEntry> {
    return userNameRepository.getNameHistory(player)
  }

  override fun getNamesByUuids(players: List<UUID>): Map<UUID, String> {

    if (players.isEmpty()) {
      return emptyMap()
    }

    val namesByUuids = userNameRepository.getNamesByUuids(players)
    val names = mutableMapOf<UUID, String>()
    namesByUuids.forEach { (uuid, name) ->
      names[uuid] = name
    }
    return names
  }

  override fun checkForNameChange(player: UUID, name: String) {
    val nameByUuid = getNameByUuid(player)
    if (nameByUuid == name) {
      return
    }

    userNameRepository.insertOrUpdateName(player, name)

    if (nameByUuid.isEmpty()) {
      return
    }

    userNameRepository.insertHistoryEntry(player, nameByUuid)
  }

  override fun getUuidByName(name: String): UUID {
    return userNameRepository.getUuidByName(name) ?: throw UuidNotFoundException()
  }
}