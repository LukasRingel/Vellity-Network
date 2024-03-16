package net.vellity.service.access.group.mysql.metadata

import net.vellity.service.access.group.PermissionGroupMetaData
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectMetaDataForGroup(private val groupId: Int) : SelectQuery<PermissionGroupMetaData> {
  override fun query(): String {
    return "select * from access_groups_meta_data where groupId = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, groupId)
  }

  override fun mapper(resultSet: ResultSet): PermissionGroupMetaData {
    return PermissionGroupMetaData(
      resultSet.getInt("id"),
      resultSet.getInt("groupId"),
      resultSet.getString("metaKey"),
      resultSet.getString("metaValue")
    )
  }
}