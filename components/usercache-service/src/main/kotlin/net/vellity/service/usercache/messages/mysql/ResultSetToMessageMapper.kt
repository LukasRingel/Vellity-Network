package net.vellity.service.usercache.messages.mysql

import net.vellity.service.usercache.message.Message
import net.vellity.service.usercache.message.MessageSentStatus
import net.vellity.utils.mysql.extensions.getContext
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.getUuid
import java.sql.ResultSet

class ResultSetToMessageMapper {
  companion object {
    fun map(resultSet: ResultSet): Message {
      return Message(
        resultSet.getInt("id"),
        resultSet.getUuid("player"),
        resultSet.getContext("context"),
        resultSet.getString("message"),
        MessageSentStatus.byId(resultSet.getInt("status")),
        resultSet.getInstant("createdAt")
      )
    }
  }
}