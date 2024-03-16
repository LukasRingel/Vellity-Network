package net.vellity.service.usercache.messages.mysql

import net.vellity.service.usercache.message.Message
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectMessageWithId(private val id: Int) : SelectQuery<Message> {
  override fun query(): String =
    "select * from usercache_users_messages where id = ?"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, id)
  }

  override fun mapper(resultSet: ResultSet): Message =
    ResultSetToMessageMapper.map(resultSet)
}