package net.vellity.service.friends.friendship.mysql

import net.vellity.service.friends.FriendshipState
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class UpdateFriendshipState(private val friendshipId: Int, private val state: FriendshipState) : UpdateQuery {
  override fun query(): String {
    return "update friends_users_friends set state = ? where id = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setEnum(1, state)
    preparedStatement.setInt(2, friendshipId)
  }
}