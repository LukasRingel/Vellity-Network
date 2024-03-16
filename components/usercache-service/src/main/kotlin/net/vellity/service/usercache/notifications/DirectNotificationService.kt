package net.vellity.service.usercache.notifications

import net.vellity.service.usercache.notification.Notification
import net.vellity.service.usercache.notification.ToggledNotification
import net.vellity.utils.context.Context
import net.vellity.utils.configuration.lifetimeDate
import net.vellity.utils.mysql.extensions.nowUtc
import org.springframework.stereotype.Service
import java.util.*

@Service
class DirectNotificationService(private val repository: NotificationRepository) : NotificationService {
  override fun getToggledNotification(
    context: Context,
    player: UUID,
    notification: Notification
  ): ToggledNotification? {
    return repository.getToggledNotification(context, player, notification)
  }

  override fun getAllNotifications(context: Context, player: UUID): List<ToggledNotification> {
    val toggledNotifications = repository.getToggledNotifications(context, player)
    val remaining = mutableListOf<ToggledNotification>()

    for (notification in Notification.values()) {
      if (toggledNotifications.none { it.notification == notification }) {
        remaining.add(
          ToggledNotification(
            id = 0,
            player = player,
            context = context,
            notification = notification,
            enabled = false,
            activeUntil = lifetimeDate,
            createdAt = nowUtc(),
            updatedAt = nowUtc()
          )
        )
      }
    }

    return toggledNotifications.plus(remaining)
  }

  override fun updateNotification(
    context: Context,
    player: UUID,
    notification: Notification,
    enabled: Boolean
  ): ToggledNotification {
    return repository.updateNotification(context, player, notification, enabled)
  }
}