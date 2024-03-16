package net.vellity.service.access.group.mysql.metadata

import net.vellity.service.access.group.PermissionGroupMetaData
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class RemoveMetaDataForGroup(private val metaData: PermissionGroupMetaData) : UpdateQuery {
  override fun query(): String {
    return "DELETE FROM access_groups_meta_data WHERE id = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, metaData.id)
  }
}