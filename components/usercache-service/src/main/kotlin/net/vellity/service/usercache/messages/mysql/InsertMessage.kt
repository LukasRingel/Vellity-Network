package net.vellity.service.usercache.messages.mysql

import net.vellity.service.usercache.message.MessageSentStatus
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.*

class InsertMessage(
  private val context: Context,
  private val player: UUID,
  private val message: String,
  private val status: MessageSentStatus
) : UpdateQuery {
  override fun query(): String =
    "insert into usercache_users_messages (player, context, message, status) values (?, ?, ?, ?)"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, player)
    preparedStatement.setContext(2, context)
    preparedStatement.setString(3, message)
    preparedStatement.setEnum(4, status)
  }
}