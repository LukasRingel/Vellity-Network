package net.vellity.service.friends.friendship.mysql.requests

import net.vellity.service.friends.FriendRequest
import net.vellity.service.friends.friendship.mysql.FriendObjectsResultSetMapper
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectRequestsSentByPlayer(private val context: Context, private val player: UUID) : SelectQuery<FriendRequest> {
  override fun query(): String {
    return "select * from friends_users_requests where player = ? and context = ? and activeUntil > now();"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, player)
    preparedStatement.setContext(2, context)
  }

  override fun mapper(resultSet: ResultSet): FriendRequest {
    return FriendObjectsResultSetMapper.resultSetToFriendRequest.apply(resultSet)
  }
}