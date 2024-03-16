package net.vellity.service.punish.punishments.mysql

import net.vellity.service.punish.punishments.*
import net.vellity.service.punish.punishments.dto.StoredPunishment
import net.vellity.utils.mysql.extensions.getContext
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.getUuid
import java.sql.ResultSet

class PunishmentResultSetMapper {
  companion object {
    fun resultSetToStoredPunishment(resultSet: ResultSet): StoredPunishment =
      StoredPunishment(
        resultSet.getInt("id"),
        PunishmentType.byId(resultSet.getInt("type")),
        resultSet.getContext("context"),
        resultSet.getUuid("player"),
        resultSet.getInt("reason"),
        emptyList(),
        emptyList(),
        resultSet.getInstant("activeUntil"),
        resultSet.getInstant("createdAt")
      )

    fun resultSetToPunishmentAction(resultSet: ResultSet): PunishmentAction =
      PunishmentAction(
        resultSet.getInt("id"),
        resultSet.getInt("punishment"),
        PunishmentActionType.byId(resultSet.getInt("type")),
        resultSet.getUuid("actor"),
        resultSet.getInstant("createdAt"),
        resultSet.getString("beforeValue"),
        resultSet.getString("afterValue")
      )

    fun resultSetToPunishmentProof(resultSet: ResultSet): PunishmentProof =
      PunishmentProof(
        resultSet.getInt("id"),
        resultSet.getInt("punishment"),
        PunishmentProofType.byId(resultSet.getInt("type")),
        resultSet.getUuid("issuer"),
        resultSet.getString("value"),
        resultSet.getInstant("createdAt"),
        resultSet.getInstant("activeUntil")
      )
  }
}