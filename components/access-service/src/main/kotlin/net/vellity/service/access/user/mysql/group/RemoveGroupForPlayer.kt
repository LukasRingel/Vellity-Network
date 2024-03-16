package net.vellity.service.access.user.mysql.group

import net.vellity.service.access.user.AssignedGroup
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class RemoveGroupForPlayer(private val assignedGroup: AssignedGroup) : UpdateQuery {
  override fun query(): String {
    return "DELETE FROM access_users_groups WHERE id = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, assignedGroup.id)
  }
}