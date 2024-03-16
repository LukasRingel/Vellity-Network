package net.vellity.service.usercache.names.mysql

import net.vellity.service.usercache.name.NameHistoryEntry
import net.vellity.service.usercache.names.UserNameRepository
import net.vellity.utils.mysql.MySqlConnection
import org.springframework.stereotype.Component
import java.util.*

@Component
class MySqlUserNameRepository(private val mySqlConnection: MySqlConnection) : UserNameRepository {
  override fun getNameByUuid(player: UUID): String =
    mySqlConnection.executeQuery(SelectNameByUuid(player)).firstOrNull() ?: ""

  override fun getNameHistory(player: UUID): List<NameHistoryEntry> =
    mySqlConnection.executeQuery(NameHistoryByUuid(player))

  override fun getNamesByUuids(players: List<UUID>): List<Pair<UUID, String>> =
    mySqlConnection.executeQuery(BulkSelectNameByUuid(players))

  override fun insertOrUpdateName(player: UUID, name: String) =
    mySqlConnection.executeUpdate(InsertOrUpdateUserNameByUuid(player, name))

  override fun insertHistoryEntry(player: UUID, name: String) =
    mySqlConnection.executeUpdate(InsertHistoryEntry(player, name))

  override fun getUuidByName(name: String): UUID? =
    mySqlConnection.executeQuery(SelectUuidByName(name)).firstOrNull()
}