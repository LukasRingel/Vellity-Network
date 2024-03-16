package net.vellity.service.friends.friendship.mysql.blocklist

import net.vellity.service.friends.BlocklistEntry
import net.vellity.service.friends.friendship.mysql.FriendObjectsResultSetMapper
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectBlocklistEntry(private val context: Context, private val player: UUID, private val target: UUID): SelectQuery<BlocklistEntry> {
  override fun query(): String {
    return "select * from friends_users_blocked where context = ? and player = ? and target = ? and activeUntil > now();"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
    preparedStatement.setUuid(3, target)
  }

  override fun mapper(resultSet: ResultSet): BlocklistEntry {
    return FriendObjectsResultSetMapper.resultSetToBlocklistEntry.apply(resultSet)
  }
}