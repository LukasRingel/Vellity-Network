package net.vellity.service.punish.punishments.mysql.update

import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class DeletePunishment(private val id: Int) : UpdateQuery {
  override fun query(): String =
    "update punish_users_punishments set activeUntil = now() where id = ?;"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, id)
  }
}