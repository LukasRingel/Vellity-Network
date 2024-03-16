package net.vellity.service.friends

import net.vellity.utils.context.Context
import java.time.Instant
import java.util.UUID

data class FriendRequest(
  val id: Int,
  val context: Context,
  val sender: UUID,
  val target: UUID,
  val activeUntil: Instant,
  val createdAt: Instant
)