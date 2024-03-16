package net.vellity.service.punish.punishments.mysql.selectActive.details

import net.vellity.service.punish.punishments.PunishmentProof
import net.vellity.service.punish.punishments.mysql.PunishmentResultSetMapper
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectProofsForPunishments(private val punishments: List<Int>) : SelectQuery<PunishmentProof> {
  override fun query(): String =
    "select * from punish_users_punishments_proofs " +
      "where punishment in (" + punishments.joinToString(",") { "?" } + ") and " +
      "activeUntil > now();"

  override fun replace(preparedStatement: PreparedStatement) {
    punishments.forEachIndexed { index, punishment ->
      preparedStatement.setInt(index + 1, punishment)
    }
  }

  override fun mapper(resultSet: ResultSet): PunishmentProof =
    PunishmentResultSetMapper.resultSetToPunishmentProof(resultSet)
}