package net.vellity.service.usercache.names.mysql

import net.vellity.service.usercache.name.NameHistoryEntry
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.getUuid
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class NameHistoryByUuid(private val player: UUID) : SelectQuery<NameHistoryEntry> {
  override fun query(): String {
    return "select * from usercache_users_names_history where player = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, player)
  }

  override fun mapper(resultSet: ResultSet): NameHistoryEntry {
    return NameHistoryEntry(
      resultSet.getInt("id"),
      resultSet.getUuid("player"),
      resultSet.getString("name"),
      resultSet.getInstant("updatedAt")
    )
  }
}