package net.vellity.service.guardian.chatlog.mysql

import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectMessagesForChatlog(private val id: Int): SelectQuery<Int> {
  override fun query(): String =
    "select message from guardian_chatlogs_messages where chatlog = ?;"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, id)
  }

  override fun mapper(resultSet: ResultSet): Int = resultSet.getInt("message")
}