package net.vellity.service.usercache.messages.mysql

import net.vellity.service.usercache.message.Message
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectMessagesForIds(private val ids: List<Int>) : SelectQuery<Message> {
  override fun query(): String =
    "select * from usercache_users_messages where id in (" + ids.joinToString(",") { "?" } + ");"

  override fun replace(preparedStatement: PreparedStatement) {
    ids.forEachIndexed { index, id ->
      preparedStatement.setInt(index + 1, id)
    }
  }

  override fun mapper(resultSet: ResultSet): Message =
    ResultSetToMessageMapper.map(resultSet)
}