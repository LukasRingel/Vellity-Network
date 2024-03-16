package net.vellity.service.access.group.mysql.metadata

import net.vellity.service.access.group.PermissionGroup
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class InsertMetaDataForGroup(private val group: PermissionGroup, private val metaData: Pair<String, String>) :
  UpdateQuery {
  override fun query(): String {
    return "INSERT INTO access_groups_meta_data (groupId, metaKey, metaValue) VALUES (?, ?, ?);"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, group.id)
    preparedStatement.setString(2, metaData.first)
    preparedStatement.setString(3, metaData.second)
  }
}