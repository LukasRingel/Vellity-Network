package net.vellity.service.punish.reason.mysql

import net.vellity.service.punish.reason.PunishmentTimeUnit
import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.service.punish.reason.Reason
import net.vellity.service.punish.reason.ReasonDuration
import net.vellity.utils.mysql.extensions.getContext
import java.sql.ResultSet

class ReasonResultSetMapper {
  companion object {
    fun resultSetToReason(resultSet: ResultSet): Reason {
      return Reason(
        resultSet.getInt("id"),
        resultSet.getContext("context"),
        resultSet.getString("name"),
        PunishmentType.byId(resultSet.getInt("punishmentType")),
        null
      )
    }

    fun resultSetToDuration(resultSet: ResultSet): ReasonDuration {
      return ReasonDuration(
        resultSet.getInt("id"),
        resultSet.getInt("reason"),
        resultSet.getInt("amount"),
        PunishmentTimeUnit.byId(resultSet.getInt("timeunit")),
        resultSet.getDouble("multiplier")
      )
    }
  }
}