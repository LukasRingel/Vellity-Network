package net.vellity.service.usercache.texture

import java.time.Instant
import java.util.UUID

data class PlayerTextureHistoryEntry(
  val player: UUID,
  val signature: String,
  val value: String,
  val updatedAt: Instant
)