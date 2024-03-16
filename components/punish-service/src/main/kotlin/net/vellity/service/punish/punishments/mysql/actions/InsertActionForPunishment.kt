package net.vellity.service.punish.punishments.mysql.actions

import net.vellity.service.punish.punishments.PunishmentActionType
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.*

class InsertActionForPunishment(
  private val punishment: Int,
  private val action: PunishmentActionType,
  private val actor: UUID,
  private val beforeValue: String,
  private val afterValue: String
) : UpdateQuery {
  override fun query(): String =
    "insert into punish_users_punishments_actions (punishment, type, actor, beforeValue, afterValue) " +
      "values (?, ?, ?, ?, ?);"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, punishment)
    preparedStatement.setEnum(2, action)
    preparedStatement.setUuid(3, actor)
    preparedStatement.setString(4, beforeValue)
    preparedStatement.setString(5, afterValue)
  }
}