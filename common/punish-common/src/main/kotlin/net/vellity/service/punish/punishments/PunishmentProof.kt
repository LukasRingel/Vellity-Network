package net.vellity.service.punish.punishments

import java.time.Instant
import java.util.*

data class PunishmentProof(
  val id: Int,
  val punishmentId: Int,
  val type: PunishmentProofType,
  val issuer: UUID,
  val value: String,
  val createdAt: Instant,
  val activeUntil: Instant
)