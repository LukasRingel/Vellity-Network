package net.vellity.service.guardian.chatlog

import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

data class Chatlog(
  val id: Int,
  val context: Context,
  val creator: UUID,
  val target: UUID,
  val createdAt: Instant,
  val activeUntil: Instant
)
