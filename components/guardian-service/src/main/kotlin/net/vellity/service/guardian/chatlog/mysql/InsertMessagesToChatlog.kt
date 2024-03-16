package net.vellity.service.guardian.chatlog.mysql

import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class InsertMessagesToChatlog(private val chatlog: Int, private val messages: List<Int>): UpdateQuery {
  override fun query(): String =
    "INSERT INTO guardian_chatlogs_messages (chatlog, message) VALUES " +
      messages.joinToString(", ") { "(?,?)" }

  override fun replace(preparedStatement: PreparedStatement) {
    var index = 1
    for (message in messages) {
      preparedStatement.setInt(index++, chatlog)
      preparedStatement.setInt(index++, message)
    }
  }
}