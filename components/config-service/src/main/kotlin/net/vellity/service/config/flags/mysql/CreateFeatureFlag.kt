package net.vellity.service.config.flags.mysql

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant

class CreateFeatureFlag(
  private val context: Context,
  private val name: String,
  private val enabled: Boolean,
  private val activeUntil: Instant
) : UpdateQuery {
  override fun query(): String {
    return "insert into config_flags (context, name, enabled, activeUntil) values (?, ?, ?, ?);"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setString(2, name)
    preparedStatement.setBoolean(3, enabled)
    preparedStatement.setInstant(4, activeUntil)
  }
}