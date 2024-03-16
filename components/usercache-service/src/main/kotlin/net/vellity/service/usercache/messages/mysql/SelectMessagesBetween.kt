package net.vellity.service.usercache.messages.mysql

import net.vellity.service.usercache.message.Message
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.Instant
import java.util.*

class SelectMessagesBetween(
  private val context: Context,
  private val player: UUID,
  private val startDate: Instant,
  private val endDate: Instant
) : SelectQuery<Message> {
  override fun query(): String =
    "select * from usercache_users_messages where context = ? and player = ? and createdAt between ? and ?"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
    preparedStatement.setInstant(3, startDate)
    preparedStatement.setInstant(4, endDate)
  }

  override fun mapper(resultSet: ResultSet): Message =
    ResultSetToMessageMapper.map(resultSet)
}