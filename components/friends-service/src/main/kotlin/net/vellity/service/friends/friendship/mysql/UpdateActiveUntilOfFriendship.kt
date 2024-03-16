package net.vellity.service.friends.friendship.mysql

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant
import java.util.*

class UpdateActiveUntilOfFriendship(
  private val context: Context,
  private val playerOne: UUID,
  private val playerTwo: UUID,
  private val activeUntil: Instant
) : UpdateQuery {
  override fun query(): String =
    "update friends_users_friends set activeUntil = ? where player = ? and friend = ? and context = ?;"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInstant(1, activeUntil)
    preparedStatement.setUuid(2, playerOne)
    preparedStatement.setUuid(3, playerTwo)
    preparedStatement.setEnum(4, context)
  }
}