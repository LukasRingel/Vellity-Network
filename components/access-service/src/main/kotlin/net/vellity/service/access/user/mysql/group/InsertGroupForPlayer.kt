package net.vellity.service.access.user.mysql.group

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.query.UpdateQuery
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.extensions.setUuid
import java.sql.PreparedStatement
import java.time.Instant
import java.util.*

class InsertGroupForPlayer(
  private val player: UUID,
  private val groupId: Int,
  private val context: Context,
  private val expireAt: Instant
) : UpdateQuery {
  override fun query(): String {
    return "INSERT INTO access_users_groups (player, `group`, context, expireAt) VALUES (?, ?, ?, ?);"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, player)
    preparedStatement.setInt(2, groupId)
    preparedStatement.setContext(3, context)
    preparedStatement.setInstant(4, expireAt)
  }
}