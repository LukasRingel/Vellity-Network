package net.vellity.service.usercache.notifications.mysql

import net.vellity.service.usercache.notification.Notification
import net.vellity.service.usercache.notification.ToggledNotification
import net.vellity.service.usercache.notifications.NotificationRepository
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.configuration.lifetimeDate
import net.vellity.utils.mysql.extensions.nowUtc
import org.springframework.stereotype.Component
import java.util.*

@Component
class MySqlNotificationRepository(private val mySqlConnection: MySqlConnection) : NotificationRepository {
  override fun getToggledNotification(
    context: Context,
    player: UUID,
    notification: Notification
  ): ToggledNotification? =
    mySqlConnection.executeQuery(SelectToggledNotification(context, player, notification)).firstOrNull()

  override fun getToggledNotifications(context: Context, player: UUID): List<ToggledNotification> =
    mySqlConnection.executeQuery(SelectAllToggledNotifications(context, player))

  override fun updateNotification(
    context: Context,
    player: UUID,
    notification: Notification,
    enabled: Boolean
  ): ToggledNotification {
    mySqlConnection.executeUpdateAndGetId(
      InsertOrUpdateNotification(
        context,
        player,
        notification,
        enabled,
        lifetimeDate
      )
    ).let {
      return ToggledNotification(
        id = it,
        player = player,
        context = context,
        notification = notification,
        enabled = enabled,
        activeUntil = lifetimeDate,
        createdAt = nowUtc(),
        updatedAt = nowUtc()
      )
    }
  }
}