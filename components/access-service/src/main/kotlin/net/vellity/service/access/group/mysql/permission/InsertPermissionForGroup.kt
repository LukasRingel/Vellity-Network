package net.vellity.service.access.group.mysql.permission

import net.vellity.service.access.group.PermissionGroup
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant

class InsertPermissionForGroup(
  private val group: PermissionGroup,
  private val permission: String,
  private val activeUntil: Instant
) : UpdateQuery {
  override fun query(): String {
    return "INSERT INTO access_groups_permissions (groupId, name, activeUntil) VALUES (?, ?, ?);"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, group.id)
    preparedStatement.setString(2, permission)
    preparedStatement.setInstant(3, activeUntil)
  }
}