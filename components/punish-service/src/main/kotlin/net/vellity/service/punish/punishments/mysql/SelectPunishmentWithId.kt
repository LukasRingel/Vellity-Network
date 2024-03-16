package net.vellity.service.punish.punishments.mysql

import net.vellity.service.punish.punishments.Punishment
import net.vellity.service.punish.punishments.dto.StoredPunishment
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectPunishmentWithId(private val id: Int): SelectQuery<StoredPunishment> {
  override fun query(): String =
    "select * from punish_users_punishments where id = ?;"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, id)
  }

  override fun mapper(resultSet: ResultSet): StoredPunishment =
    PunishmentResultSetMapper.resultSetToStoredPunishment(resultSet)
}