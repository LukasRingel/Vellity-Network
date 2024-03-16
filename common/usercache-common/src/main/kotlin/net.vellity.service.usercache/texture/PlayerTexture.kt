package net.vellity.service.usercache.texture

import java.util.UUID

data class PlayerTexture(
  val player: UUID,
  val signature: String,
  val value: String
)