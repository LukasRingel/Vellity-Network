package net.vellity.service.access.user.mysql.group.history

import net.vellity.service.access.user.GroupHistoryEntry
import net.vellity.utils.mysql.extensions.getContext
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.getUuid
import java.sql.ResultSet

class ResultSetToHistoryMapper {
  companion object {
    fun map(resultSet: ResultSet): GroupHistoryEntry {
      return GroupHistoryEntry(
        resultSet.getInt("id"),
        resultSet.getContext("context"),
        resultSet.getUuid("player"),
        resultSet.getInt("group"),
        resultSet.getInstant("expireAt")
      )
    }
  }
}