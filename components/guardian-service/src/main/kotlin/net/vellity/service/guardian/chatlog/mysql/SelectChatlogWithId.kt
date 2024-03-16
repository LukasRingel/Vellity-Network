package net.vellity.service.guardian.chatlog.mysql

import net.vellity.service.guardian.chatlog.Chatlog
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectChatlogWithId(private val id: Int) : SelectQuery<Chatlog> {
  override fun query(): String =
    "select * from guardian_chatlogs where id = ?"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, id)
  }

  override fun mapper(resultSet: ResultSet): Chatlog =
    ResultSetToChatlogMapper.map(resultSet)
}