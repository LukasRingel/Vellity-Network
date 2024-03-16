package net.vellity.service.punish.reason.mysql.duration

import net.vellity.service.punish.reason.PunishmentTimeUnit
import net.vellity.utils.configuration.lifetimeDate
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class InsertDurationForReason(
  private val reasonId: Int,
  private val durationAmount: Int,
  private val durationTimeUnit: PunishmentTimeUnit,
  private val multiplier: Double
) : UpdateQuery {
  override fun query(): String =
    "insert into punish_reasons_durations (reason, timeunit, amount, multiplier, activeUntil) values (?,?,?,?,?);"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, reasonId)
    preparedStatement.setEnum(2, durationTimeUnit)
    preparedStatement.setInt(3, durationAmount)
    preparedStatement.setDouble(4, multiplier)
    preparedStatement.setInstant(5, lifetimeDate)
  }
}