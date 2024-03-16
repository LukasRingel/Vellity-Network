package net.vellity.service.punish.reason

import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.utils.context.Context

interface ReasonsRepository {
  fun getAllReasons(): List<Reason>

  fun createReason(context: Context, name: String, type: PunishmentType): Reason

  fun deleteReason(id: Int)

  fun createDuration(
    reason: Reason,
    timeunitAmount: Int,
    timeunit: PunishmentTimeUnit,
    multiplier: Double
  ): ReasonDuration

  fun deleteDuration(reason: Reason)
}