package net.vellity.service.access.group.mysql.group

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.query.UpdateQuery
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setInstant
import java.sql.PreparedStatement
import java.time.Instant

class InsertGroup(private val context: Context, private val name: String, private val activeUntil: Instant) : UpdateQuery {
  override fun query(): String {
    return "INSERT INTO access_groups (context, name, activeUntil) VALUES (?, ?, ?);"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setString(2, name)
    preparedStatement.setInstant(3, activeUntil)
  }
}