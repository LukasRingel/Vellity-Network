package net.vellity.service.punish.reason

import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.utils.context.Context

data class Reason(
  val id: Int,
  val context: Context,
  val name: String,
  val type: PunishmentType,
  var duration: ReasonDuration?,
)
