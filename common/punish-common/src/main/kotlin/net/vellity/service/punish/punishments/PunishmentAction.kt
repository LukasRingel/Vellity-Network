package net.vellity.service.punish.punishments

import java.time.Instant
import java.util.*

data class PunishmentAction(
  val id: Int,
  val punishmentId: Int,
  val actionType: PunishmentActionType,
  val actor: UUID,
  val createdAt: Instant,
  val beforeValue: String?,
  val afterValue: String?
)