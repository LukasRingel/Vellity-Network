package net.vellity.service.worker.worker.access

import net.vellity.service.worker.api.scheduled.ScheduledWorker
import net.vellity.service.worker.history.EnableHistory
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.extensions.*
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

@Component
@EnableHistory("access_permissions_expired")
class MoveExpiredPermissionsToHistory(private val mySqlConnection: MySqlConnection) :
  ScheduledWorker("access-permissions-expired", 30, TimeUnit.MINUTES) {
  override fun run() {
    migrateUserPermissions()
    migrateGroupPermissions()
  }

  private fun migrateGroupPermissions() {
    mySqlConnection.executeQuery("select * from access_groups_permissions where activeUntil < now()") { resultSet ->
      GroupPermissionEntry(
        resultSet.getInt("id"),
        resultSet.getInt("groupId"),
        resultSet.getString("name"),
        resultSet.getInstant("activeUntil")
      )
    }
      .forEach { entry ->
        mySqlConnection.executeUpdate(
          "insert into access_groups_permissions_history (groupId, permission, activeUntil) values (?,?,?)",
          replacements = {
            it.setInt(1, entry.group)
            it.setString(2, entry.permission)
            it.setInstant(3, entry.activeUntil)
          }
        )
        mySqlConnection.executeUpdate("delete from access_groups_permissions where id = ?", replacements = {
          it.setInt(1, entry.id)
        })
      }
  }

  private fun migrateUserPermissions() {
    mySqlConnection.executeQuery("select * from access_users_permissions where expireAt < now()") { resultSet ->
      UserPermissionEntry(
        resultSet.getInt("id"),
        resultSet.getContext("context"),
        resultSet.getUuid("player"),
        resultSet.getString("permission"),
        resultSet.getInstant("expireAt")
      )
    }
      .forEach { entry ->
        mySqlConnection.executeUpdate(
          "insert into access_users_permissions_history (context, player, permission, expireAt) values (?,?,?,?)",
          replacements = {
            it.setContext(1, entry.context)
            it.setUuid(2, entry.player)
            it.setString(3, entry.permission)
            it.setInstant(4, entry.expireAt)
          }
        )
        mySqlConnection.executeUpdate("delete from access_users_permissions where id = ?", replacements = {
          it.setInt(1, entry.id)
        })
      }
  }

  private data class GroupPermissionEntry(val id: Int, val group: Int, val permission: String, val activeUntil: Instant)
  private data class UserPermissionEntry(val id: Int, val context: Context, val player: UUID, val permission: String, val expireAt: Instant)
}