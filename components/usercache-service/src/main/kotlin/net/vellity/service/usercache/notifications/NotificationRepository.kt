package net.vellity.service.usercache.notifications

import net.vellity.service.usercache.notification.Notification
import net.vellity.service.usercache.notification.ToggledNotification
import net.vellity.utils.context.Context
import java.util.*

interface NotificationRepository {
  fun getToggledNotification(context: Context, player: UUID, notification: Notification): ToggledNotification?

  fun getToggledNotifications(context: Context, player: UUID): List<ToggledNotification>

  fun updateNotification(context: Context, player: UUID, notification: Notification, enabled: Boolean): ToggledNotification
}