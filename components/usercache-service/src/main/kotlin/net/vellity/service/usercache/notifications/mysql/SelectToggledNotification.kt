package net.vellity.service.usercache.notifications.mysql

import net.vellity.service.usercache.notification.Notification
import net.vellity.service.usercache.notification.ToggledNotification
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectToggledNotification(
  private val context: Context,
  private val player: UUID,
  private val notification: Notification
) : SelectQuery<ToggledNotification> {
  override fun query(): String =
    "select * from usercache_users_notifications " +
      "where context = ? and " +
      "player = ? and " +
      "notification = ? and " +
      "activeUntil > now();"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
    preparedStatement.setEnum(3, notification)
  }

  override fun mapper(resultSet: ResultSet): ToggledNotification =
    NotificationResultSetMapper.resultSetToToggledNotification(resultSet)
}