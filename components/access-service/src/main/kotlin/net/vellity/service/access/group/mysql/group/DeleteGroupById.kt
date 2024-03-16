package net.vellity.service.access.group.mysql.group

import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class DeleteGroupById(private val id: Int) : UpdateQuery {
  override fun query(): String {
    return "update access_groups set activeUntil = now() WHERE id = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, id)
  }
}