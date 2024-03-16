package net.vellity.service.guardian.chatlog

import java.time.Instant
import java.util.*

data class ChatlogMessage(
  val id: Int,
  val sender: UUID,
  val message: String,
  val createdAt: Instant,
)