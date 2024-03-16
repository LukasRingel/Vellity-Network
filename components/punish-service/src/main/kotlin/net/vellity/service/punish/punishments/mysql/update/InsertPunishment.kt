package net.vellity.service.punish.punishments.mysql.update

import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.service.punish.reason.Reason
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant
import java.util.*

class InsertPunishment(
  private val context: Context,
  private val type: PunishmentType,
  private val player: UUID,
  private val reason: Reason,
  private val activeUntil: Instant
) : UpdateQuery {
  override fun query(): String =
    "insert into punish_users_punishments(context, type, player, reason, activeUntil) values (?,?,?,?,?)"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setEnum(2, type)
    preparedStatement.setUuid(3, player)
    preparedStatement.setInt(4, reason.id)
    preparedStatement.setInstant(5, activeUntil)
  }
}