package net.vellity.service.friends.friendship.mysql.requests

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant
import java.util.*

class InsertFriendRequest(
  private val context: Context,
  private val player: UUID,
  private val target: UUID,
  private val activeUntil: Instant
) : UpdateQuery {
  override fun query(): String {
    return "insert into friends_users_requests (context, player, target, activeUntil) values (?, ?, ?, ?)"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
    preparedStatement.setUuid(3, target)
    preparedStatement.setInstant(4, activeUntil)
  }
}