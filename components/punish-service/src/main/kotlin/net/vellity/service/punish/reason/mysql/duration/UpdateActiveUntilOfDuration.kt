package net.vellity.service.punish.reason.mysql.duration

import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class UpdateActiveUntilOfDuration(private val id: Int): UpdateQuery {
  override fun query(): String =
    "update punish_reasons_durations set activeUntil = now() where id = ?;"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, id)
  }
}