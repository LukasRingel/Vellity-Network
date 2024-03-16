package net.vellity.service.punish.reason.mysql

import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.service.punish.reason.PunishmentTimeUnit
import net.vellity.service.punish.reason.Reason
import net.vellity.service.punish.reason.ReasonDuration
import net.vellity.service.punish.reason.ReasonsRepository
import net.vellity.service.punish.reason.mysql.duration.InsertDurationForReason
import net.vellity.service.punish.reason.mysql.duration.SelectDurationsForReports
import net.vellity.service.punish.reason.mysql.duration.UpdateActiveUntilOfDuration
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import org.springframework.stereotype.Component

@Component
class MySqlReasonRepository(private val mySqlConnection: MySqlConnection) : ReasonsRepository {
  override fun getAllReasons(): List<Reason> {
    val reasons = mySqlConnection.executeQuery(SelectAllReasonsWithoutDurations())
    if (reasons.isEmpty()) {
      return emptyList()
    }
    mySqlConnection.executeQuery(SelectDurationsForReports(reasons.map { it.id })).forEach { duration ->
      reasons.first { it.id == duration.reasonId }.duration = duration
    }
    return reasons
  }

  override fun createReason(context: Context, name: String, type: PunishmentType): Reason {
    val id = mySqlConnection.executeUpdateAndGetId(InsertReason(context, name, type))
    return Reason(id, context, name, type, null)
  }

  override fun deleteReason(id: Int) {
    mySqlConnection.executeUpdate(UpdateActiveUntilOfReason(id))
  }

  override fun createDuration(
    reason: Reason,
    timeunitAmount: Int,
    timeunit: PunishmentTimeUnit,
    multiplier: Double
  ): ReasonDuration {
    val id = mySqlConnection.executeUpdateAndGetId(
      InsertDurationForReason(
        reason.id,
        timeunitAmount,
        timeunit,
        multiplier
      )
    )
    return ReasonDuration(id, reason.id, timeunitAmount, timeunit, multiplier)
  }

  override fun deleteDuration(reason: Reason) {
    mySqlConnection.executeUpdate(UpdateActiveUntilOfDuration(reason.id))
  }
}