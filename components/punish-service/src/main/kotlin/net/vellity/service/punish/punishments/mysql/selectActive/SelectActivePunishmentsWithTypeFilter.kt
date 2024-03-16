package net.vellity.service.punish.punishments.mysql.selectActive

import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.service.punish.punishments.dto.StoredPunishment
import net.vellity.service.punish.punishments.mysql.PunishmentResultSetMapper
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectActivePunishmentsWithTypeFilter(
  private val context: Context,
  private val player: UUID,
  private val type: PunishmentType
) : SelectQuery<StoredPunishment> {
  override fun query(): String =
    "select * from punish_users_punishments where context = ? and player = ? and type = ? and activeUntil > now();"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
    preparedStatement.setEnum(3, type)
  }

  override fun mapper(resultSet: ResultSet): StoredPunishment =
    PunishmentResultSetMapper.resultSetToStoredPunishment(resultSet)
}