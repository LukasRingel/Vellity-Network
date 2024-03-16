package net.vellity.service.friends.friendship.mysql.friends

import net.vellity.service.friends.FriendshipState
import net.vellity.utils.configuration.lifetimeDate
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.*
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.*

class InsertFriendship(
  private val context: Context,
  private val player: UUID,
  private val friend: UUID
) : UpdateQuery {
  override fun query(): String {
    return "insert into friends_users_friends(context, player, friend, state, activeUntil) values (?,?,?,?,?);"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
    preparedStatement.setUuid(3, friend)
    preparedStatement.setEnum(4, FriendshipState.NORMAL)
    preparedStatement.setInstant(5, lifetimeDate)
  }
}