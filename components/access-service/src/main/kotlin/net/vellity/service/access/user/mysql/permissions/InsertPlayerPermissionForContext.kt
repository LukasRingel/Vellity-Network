package net.vellity.service.access.user.mysql.permissions

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.query.UpdateQuery
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.extensions.setUuid
import java.sql.PreparedStatement
import java.time.Instant
import java.util.*

class InsertPlayerPermissionForContext(
  private val player: UUID,
  private val context: Context,
  private val permission: String,
  private val expireAt: Instant
) : UpdateQuery {
  override fun query(): String {
    return "INSERT INTO access_users_permissions (player, context, permission, expireAt) VALUES (?, ?, ?, ?);"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, player)
    preparedStatement.setContext(2, context)
    preparedStatement.setString(3, permission)
    preparedStatement.setInstant(4, expireAt)
  }
}