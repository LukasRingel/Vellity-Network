package net.vellity.service.usercache.message

import net.vellity.utils.context.Context
import java.time.Instant
import java.util.UUID

data class Message(
  val id: Int,
  val player: UUID,
  val context: Context,
  val message: String,
  val sentStatus: MessageSentStatus,
  val createdAt: Instant
)