package net.vellity.service.usercache.notifications.mysql

import net.vellity.service.usercache.notification.Notification
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant
import java.util.*

class InsertOrUpdateNotification(
  private val context: Context,
  private val player: UUID,
  private val notification: Notification,
  private val enabled: Boolean,
  private val activeUntil: Instant
) : UpdateQuery {
  override fun query(): String =
    "insert into usercache_users_notifications (context, player, notification, status, activeUntil) " +
      "values (?, ?, ?, ?, ?) on duplicate key update status = ?, activeUntil = ?;"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
    preparedStatement.setEnum(3, notification)
    preparedStatement.setBoolean(4, enabled)
    preparedStatement.setInstant(5, activeUntil)
    preparedStatement.setBoolean(6, enabled)
    preparedStatement.setInstant(7, activeUntil)
  }
}