package net.vellity.service.punish.punishments.mysql.selectActive

import net.vellity.service.punish.punishments.Punishment
import net.vellity.service.punish.punishments.dto.StoredPunishment
import net.vellity.service.punish.punishments.mysql.PunishmentResultSetMapper
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectActivePunishmentsWithoutTypeFilter(private val context: Context, private val player: UUID): SelectQuery<StoredPunishment> {
  override fun query(): String =
    "select * from punish_users_punishments where context = ? and player = ? and activeUntil > now();"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
  }

  override fun mapper(resultSet: ResultSet): StoredPunishment =
    PunishmentResultSetMapper.resultSetToStoredPunishment(resultSet)
}