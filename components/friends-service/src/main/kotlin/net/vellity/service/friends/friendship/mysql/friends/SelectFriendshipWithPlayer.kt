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

class SelectFriendshipWithPlayer(private val context: Context, private val player: UUID, private val friend: UUID) :
  SelectQuery<Friendship> {
  override fun query(): String {
    return "SELECT * FROM friends_users_friends WHERE player = ? AND friend = ? and context = ? and activeUntil > now();"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, player)
    preparedStatement.setUuid(2, friend)
    preparedStatement.setContext(3, context)
  }

  override fun mapper(resultSet: ResultSet): Friendship {
    return FriendObjectsResultSetMapper.resultSetToFriendship.apply(resultSet)
  }
}