package net.vellity.service.access.group.log.mysql

import net.vellity.service.access.group.log.GroupChangeRepository
import net.vellity.service.access.group.log.model.GroupMetaDataChange
import net.vellity.service.access.group.log.model.GroupPermissionChange
import net.vellity.utils.mysql.MySqlConnection
import org.springframework.stereotype.Component

@Component
class MySqlGroupChangeRepository(private val mySqlConnection: MySqlConnection) : GroupChangeRepository {
  override fun logMetaDataChange(change: GroupMetaDataChange) {
    mySqlConnection.executeUpdate(
      "insert into access_audit_groups_meta_data (groupId, metadata, oldValue, newValue) values (?,?,?,?);",
    ) {
      it.setInt(1, change.group)
      it.setString(2, change.metaData)
      it.setString(3, change.oldValue)
      it.setString(4, change.newValue)
    }
  }

  override fun logPermissionChange(change: GroupPermissionChange) {
    mySqlConnection.executeUpdate(
      "insert into access_audit_groups_permissions (groupId, permission, oldValue, newValue) values (?,?,?,?);",
    ) {
      it.setInt(1, change.group)
      it.setString(2, change.permission)
      it.setBoolean(3, change.oldValue)
      it.setBoolean(4, change.newValue)
    }
  }
}