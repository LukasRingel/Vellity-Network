package net.vellity.service.usercache.notifications

import net.vellity.service.usercache.notification.Notification
import net.vellity.service.usercache.notification.ToggledNotification
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

interface NotificationController {
  @GetMapping("/notifications")
  fun getToggledNotification(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("notification") notification: Notification
  ): ResponseEntity<ToggledNotification?>

  @GetMapping("/notifications/all")
  fun getAllNotifications(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID
  ): ResponseEntity<List<ToggledNotification>>

  @PutMapping("/notifications")
  fun updateNotification(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("notification") notification: Notification,
    @RequestParam("enabled") enabled: Boolean
  ): ResponseEntity<ToggledNotification>
}