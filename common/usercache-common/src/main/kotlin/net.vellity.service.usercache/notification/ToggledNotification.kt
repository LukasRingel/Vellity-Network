package net.vellity.service.usercache.notification

import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

data class ToggledNotification(
  val id: Int,
  val player: UUID,
  val context: Context,
  val notification: Notification,
  var enabled: Boolean,
  val activeUntil: Instant,
  val createdAt: Instant,
  val updatedAt: Instant
)