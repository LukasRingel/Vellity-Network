package net.vellity.service.usercache.messages.mysql

import net.vellity.service.usercache.message.Message
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectLastMessages(private val context: Context, private val player: UUID, private val limit: Int) :
  SelectQuery<Message> {
  override fun query(): String =
    "select * from usercache_users_messages where context = ? and player = ? order by createdAt desc limit ?"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
    preparedStatement.setInt(3, limit)
  }

  override fun mapper(resultSet: ResultSet): Message =
    ResultSetToMessageMapper.map(resultSet)
}