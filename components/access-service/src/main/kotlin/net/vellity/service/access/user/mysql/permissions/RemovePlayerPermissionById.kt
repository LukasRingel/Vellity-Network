package net.vellity.service.access.user.mysql.permissions

import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class RemovePlayerPermissionById(private val id: Int) : UpdateQuery {
  override fun query(): String {
    return "DELETE FROM access_users_permissions WHERE id = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, id)
  }
}