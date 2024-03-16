package net.vellity.service.friends.friendship.mysql.friends

import net.vellity.service.friends.Friendship
import net.vellity.service.friends.friendship.mysql.FriendObjectsResultSetMapper
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectFriendships(
  private val context: Context,
  private val player: UUID,
  private val limit: Int
) : SelectQuery<Friendship> {
  override fun query(): String {
    return "SELECT * FROM friends_users_friends WHERE player = ? and context = ? and activeUntil > now() limit ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, player)
    preparedStatement.setContext(2, context)
    preparedStatement.setInt(3, limit)
  }

  override fun mapper(resultSet: ResultSet): Friendship {
    return FriendObjectsResultSetMapper.resultSetToFriendship.apply(resultSet)
  }
}