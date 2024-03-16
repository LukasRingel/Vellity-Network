package net.vellity.service.friends

import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

data class BlocklistEntry(
  val id: Int,
  val context: Context,
  val player: UUID,
  val target: UUID,
  val activeUntil: Instant,
  val createdAt: Instant
)