package net.vellity.service.guardian.chatlog.mysql

import net.vellity.service.guardian.chatlog.Chatlog
import net.vellity.utils.mysql.extensions.getContext
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.getUuid
import java.sql.ResultSet

class ResultSetToChatlogMapper {
  companion object {
    fun map(resultSet: ResultSet): Chatlog {
      return Chatlog(
        resultSet.getInt("id"),
        resultSet.getContext("context"),
        resultSet.getUuid("creator"),
        resultSet.getUuid("target"),
        resultSet.getInstant("createdAt"),
        resultSet.getInstant("activeUntil")
      )
    }
  }
}