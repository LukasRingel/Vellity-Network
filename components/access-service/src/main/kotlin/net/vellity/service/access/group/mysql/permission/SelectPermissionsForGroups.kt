package net.vellity.service.access.group.mysql.permission

import net.vellity.service.access.group.GroupPermission
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectPermissionsForGroups(private val groups: List<Int>) : SelectQuery<GroupPermission> {
  override fun query(): String =
    "select * from access_groups_permissions where groupId in (" +
      groups.joinToString(",") { "?" } + ") and activeUntil > now();"


  override fun replace(preparedStatement: PreparedStatement) {
    groups.forEachIndexed { index, groupId ->
      preparedStatement.setInt(index + 1, groupId)
    }
  }

  override fun mapper(resultSet: ResultSet): GroupPermission =
    GroupPermission(
      resultSet.getInt("id"),
      resultSet.getInt("groupId"),
      resultSet.getString("name")
    )
}