package net.vellity.service.access.group.mysql.metadata

import net.vellity.service.access.group.PermissionGroupMetaData
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectMetaDataForGroups(private val groups: List<Int>) : SelectQuery<PermissionGroupMetaData> {
  override fun query(): String =
    "select * from access_groups_meta_data where groupId in (" + groups.joinToString(",") { "?" } + ");"


  override fun replace(preparedStatement: PreparedStatement) {
    groups.forEachIndexed { index, groupId ->
      preparedStatement.setInt(index + 1, groupId)
    }
  }

  override fun mapper(resultSet: ResultSet): PermissionGroupMetaData =
    PermissionGroupMetaData(
      resultSet.getInt("id"),
      resultSet.getInt("groupId"),
      resultSet.getString("metaKey"),
      resultSet.getString("metaValue")
    )
}