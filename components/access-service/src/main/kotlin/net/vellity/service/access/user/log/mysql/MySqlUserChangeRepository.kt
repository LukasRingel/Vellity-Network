package net.vellity.service.access.user.log.mysql

import net.vellity.service.access.user.log.UserChangeRepository
import net.vellity.service.access.user.log.model.UserGroupChange
import net.vellity.service.access.user.log.model.UserPermissionChange
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.extensions.setUuid
import org.springframework.stereotype.Component

@Component
class MySqlUserChangeRepository(private val mySqlConnection: MySqlConnection) : UserChangeRepository {
  override fun logGroupChange(change: UserGroupChange) {
    mySqlConnection.executeUpdate(
      "insert into access_audit_users_groups (player, groupId, expireAt, oldValue, newValue) values (?,?,?,?,?);",
    ) {
      it.setUuid(1, change.player)
      it.setInt(2, change.group)
      it.setInstant(3, change.expireAt)
      it.setBoolean(4, change.oldValue)
      it.setBoolean(5, change.newValue)
    }
  }

  override fun logPermissionChange(change: UserPermissionChange) {
    mySqlConnection.executeUpdate(
      "insert into access_audit_users_permissions (player, permission, expireAt, context, oldValue, newValue) values (?,?,?,?,?,?);",
    ) {
      it.setUuid(1, change.player)
      it.setString(2, change.permission)
      it.setInstant(3, change.expireAt)
      it.setContext(4, change.context)
      it.setBoolean(5, change.oldValue)
      it.setBoolean(6, change.newValue)
    }
  }
}