package net.vellity.service.usercache.name

import java.time.Instant
import java.util.*

data class NameHistoryEntry(
  val id: Int,
  val uuid: UUID,
  val name: String,
  val updatedAt: Instant
)