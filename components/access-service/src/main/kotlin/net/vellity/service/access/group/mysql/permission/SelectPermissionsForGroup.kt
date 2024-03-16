package net.vellity.service.access.group.mysql.permission

import net.vellity.service.access.group.GroupPermission
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectPermissionsForGroup(private val groupId: Int) : SelectQuery<GroupPermission> {
  override fun query(): String =
    "select * from access_groups_permissions where groupId = ? and activeUntil > now()"


  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, groupId)
  }

  override fun mapper(resultSet: ResultSet): GroupPermission =
    GroupPermission(
      resultSet.getInt("id"),
      resultSet.getInt("groupId"),
      resultSet.getString("name")
    )
}