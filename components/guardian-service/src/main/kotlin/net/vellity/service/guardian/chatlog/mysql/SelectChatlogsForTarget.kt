package net.vellity.service.guardian.chatlog.mysql

import net.vellity.service.guardian.chatlog.Chatlog
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectChatlogsForTarget(private val context: Context, private val target: UUID): SelectQuery<Chatlog> {
  override fun query(): String =
    "select * from guardian_chatlogs where context = ? and target = ? and activeUntil > now()"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, target)
  }

  override fun mapper(resultSet: ResultSet): Chatlog =
    ResultSetToChatlogMapper.map(resultSet)
}