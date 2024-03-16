package net.vellity.service.access.user

import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

data class GroupHistoryEntry(
  val id: Int,
  val context: Context,
  val player: UUID,
  val group: Int,
  val expireAt: Instant
)