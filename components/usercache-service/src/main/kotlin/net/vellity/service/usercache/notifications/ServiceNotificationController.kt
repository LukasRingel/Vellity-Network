package net.vellity.service.usercache.notifications

import net.vellity.service.usercache.notification.Notification
import net.vellity.service.usercache.notification.ToggledNotification
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ServiceNotificationController(private val notificationService: NotificationService) : NotificationController {
  override fun getToggledNotification(
    context: Context,
    player: UUID,
    notification: Notification
  ): ResponseEntity<ToggledNotification?> {
    return ResponseEntity.ok(
      notificationService.getToggledNotification(
        context,
        player,
        notification
      )
    )
  }

  override fun getAllNotifications(context: Context, player: UUID): ResponseEntity<List<ToggledNotification>> {
    return ResponseEntity.ok(notificationService.getAllNotifications(context, player))
  }

  override fun updateNotification(
    context: Context,
    player: UUID,
    notification: Notification,
    enabled: Boolean
  ): ResponseEntity<ToggledNotification> {
    return ResponseEntity.ok(
      notificationService.updateNotification(
        context,
        player,
        notification,
        enabled
      )
    )
  }
}