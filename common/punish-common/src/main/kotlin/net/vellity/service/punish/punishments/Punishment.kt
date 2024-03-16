package net.vellity.service.punish.punishments

import net.vellity.service.punish.reason.Reason
import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

data class Punishment(
  val id: Int,
  val type: PunishmentType,
  val context: Context,
  val player: UUID,
  var reason: Reason?,
  var actions: List<PunishmentAction>,
  var proofs: List<PunishmentProof>,
  val activeUntil: Instant,
  val createdAt: Instant
)