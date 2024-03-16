package net.vellity.service.access.group.mysql.group

import net.vellity.service.access.group.PermissionGroup
import net.vellity.utils.mysql.query.SelectQuery
import net.vellity.utils.mysql.extensions.getContext
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectAllPermissionGroups : SelectQuery<PermissionGroup> {
  override fun query(): String {
    return "select * from access_groups where activeUntil > now();;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    // Nothing to do here
  }

  override fun mapper(resultSet: ResultSet): PermissionGroup {
    return PermissionGroup(
      resultSet.getInt("id"),
      resultSet.getContext("context"),
      resultSet.getString("name"),
      mutableListOf(),
      mutableListOf()
    )
  }
}