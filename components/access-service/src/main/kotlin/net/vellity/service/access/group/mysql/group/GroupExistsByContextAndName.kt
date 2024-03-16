package net.vellity.service.access.group.mysql.group

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.query.ExistsQuery
import net.vellity.utils.mysql.extensions.setContext
import java.sql.PreparedStatement

class GroupExistsByContextAndName(private val context: Context, private val name: String) : ExistsQuery {
  override fun query(): String {
    return "SELECT 1 FROM access_groups WHERE context = ? AND name = ? and activeUntil > now();"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setString(2, name)
  }
}