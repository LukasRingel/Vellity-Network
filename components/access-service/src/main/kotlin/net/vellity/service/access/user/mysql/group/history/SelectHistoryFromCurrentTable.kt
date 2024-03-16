package net.vellity.service.access.user.mysql.group.history

import net.vellity.service.access.user.GroupHistoryEntry
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectHistoryFromCurrentTable(private val playerId: UUID, private val context: Context) :
  SelectQuery<GroupHistoryEntry> {
  override fun query(): String =
    "select * from access_users_groups where player = ? and " +
      "(context = ? or context = " + Context.ALL.ordinal + ") and expireAt < now()"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, playerId)
    preparedStatement.setInt(2, context.ordinal)
  }

  override fun mapper(resultSet: ResultSet): GroupHistoryEntry =
    ResultSetToHistoryMapper.map(resultSet)
}