package net.vellity.service.access.group.mysql.permission

import net.vellity.service.access.group.GroupPermission
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class RemovePermissionForGroup(private val permission: GroupPermission) : UpdateQuery {
  override fun query(): String {
    return "update access_groups_permissions set activeUntil = now() WHERE id = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, permission.id)
  }
}