package net.vellity.service.punish.reason.mysql

import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.utils.context.Context
import net.vellity.utils.configuration.lifetimeDate
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class InsertReason(private val context: Context, private val name: String, private val type: PunishmentType) :
  UpdateQuery {
  override fun query(): String =
    "insert into punish_reasons (context, name, punishmentType, activeUntil) values (?,?,?,?);"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setString(2, name)
    preparedStatement.setEnum(3, type)
    preparedStatement.setInstant(4, lifetimeDate)
  }
}