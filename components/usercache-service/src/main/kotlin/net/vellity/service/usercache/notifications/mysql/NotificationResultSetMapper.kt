package net.vellity.service.usercache.notifications.mysql

import net.vellity.service.usercache.notification.Notification
import net.vellity.service.usercache.notification.ToggledNotification
import net.vellity.utils.mysql.extensions.getContext
import net.vellity.utils.mysql.extensions.getUuid
import java.sql.ResultSet

class NotificationResultSetMapper {
  companion object {
    fun resultSetToToggledNotification(resultSet: ResultSet): ToggledNotification {
      return ToggledNotification(
        id = resultSet.getInt("id"),
        player = resultSet.getUuid("player"),
        context = resultSet.getContext("context"),
        notification = Notification.byId(resultSet.getInt("notification")),
        enabled = resultSet.getBoolean("status"),
        activeUntil = resultSet.getTimestamp("activeUntil").toInstant(),
        createdAt = resultSet.getTimestamp("createdAt").toInstant(),
        updatedAt = resultSet.getTimestamp("updatedAt").toInstant()
      )
    }
  }
}