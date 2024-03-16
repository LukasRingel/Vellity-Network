package net.vellity.service.config.flags.mysql

import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant

class UpdateFeatureFlag(private val id: Int, private val enabled: Boolean, private val activeUntil: Instant) :
  UpdateQuery {
  override fun query(): String {
    return "update config_flags set enabled = ?, activeUntil = ? where id = ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setBoolean(1, enabled)
    preparedStatement.setInstant(2, activeUntil)
    preparedStatement.setInt(3, id)
  }
}