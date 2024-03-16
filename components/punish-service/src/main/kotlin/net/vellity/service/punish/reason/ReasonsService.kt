package net.vellity.service.punish.reason

import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.utils.context.Context

interface ReasonsService {
  fun getReasons(context: Context): List<Reason>

  fun getReasonById(id: Int): Reason?

  fun createReason(context: Context, name: String, type: PunishmentType): Reason

  fun deleteReason(id: Int)

  fun createOrUpdateDuration(reasonId: Int, amount: Int, timeunit: PunishmentTimeUnit, multiplier: Double): ReasonDuration
}