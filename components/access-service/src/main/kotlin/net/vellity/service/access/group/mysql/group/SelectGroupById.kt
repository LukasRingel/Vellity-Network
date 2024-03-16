package net.vellity.service.access.group.mysql.group

import net.vellity.service.access.group.PermissionGroup
import net.vellity.utils.mysql.query.SelectQuery
import net.vellity.utils.mysql.extensions.getContext
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectGroupById(private val groupId: Int) : SelectQuery<PermissionGroup> {
  override fun query(): String {
    return "select * from access_groups where id = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, groupId)
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