package net.vellity.service.access.user.mysql

import net.vellity.service.access.group.PermissionGroup
import net.vellity.service.access.group.PermissionGroupService
import net.vellity.service.access.user.*
import net.vellity.service.access.user.mysql.group.InsertGroupForPlayer
import net.vellity.service.access.user.mysql.group.SelectAllGroups
import net.vellity.service.access.user.mysql.group.SelectPlayerGroupsForContext
import net.vellity.service.access.user.mysql.group.history.SelectHistoryFromCurrentTable
import net.vellity.service.access.user.mysql.group.history.SelectHistoryFromHistoryTable
import net.vellity.service.access.user.mysql.permissions.InsertPlayerPermissionForContext
import net.vellity.service.access.user.mysql.permissions.RemovePlayerPermissionById
import net.vellity.service.access.user.mysql.permissions.SelectAllPermissions
import net.vellity.service.access.user.mysql.permissions.SelectPlayerPermissionsForContext
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.configuration.lifetimeDate
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class MySqlPermissionUserRepository(
  private val connection: MySqlConnection,
  private val permissionGroupService: PermissionGroupService
) : PermissionUserRepository {
  override fun getPermissionUser(player: UUID): PermissionUser {
    val permissionUser = PermissionUser(player, mutableListOf(), mutableListOf())
    connection.executeQuery(SelectAllGroups(player)).let { groups ->
      groups.forEach { group ->
        group.toAssignedGroup(permissionGroupService)?.let { assignedGroup ->
          permissionUser.groups = permissionUser.groups.plus(assignedGroup)
        }
      }
    }
    connection.executeQuery(SelectAllPermissions(player)).let { permissions ->
      permissions.forEach { permission ->
        permissionUser.permissions = permissionUser.permissions.plus(permission)
      }
    }
    return permissionUser
  }

  override fun getPermissionUser(player: UUID, context: Context): PermissionUser {
    val permissionUser = PermissionUser(player, mutableListOf(), mutableListOf())
    connection.executeQuery(SelectPlayerGroupsForContext(player, context)).let { groups ->
      groups.forEach { group ->
        group.toAssignedGroup(permissionGroupService)?.let { assignedGroup ->
          permissionUser.groups = permissionUser.groups.plus(assignedGroup)
        }
      }
    }
    connection.executeQuery(SelectPlayerPermissionsForContext(player, context)).let { permissions ->
      permissions.forEach { permission ->
        permissionUser.permissions = permissionUser.permissions.plus(permission)
      }
    }
    return permissionUser
  }

  override fun addPermissionForUser(player: UUID, permission: String): AssignedPermission {
    return addPermissionForUser(player, permission, Context.ALL)
  }

  override fun addPermissionForUser(player: UUID, permission: String, context: Context): AssignedPermission {
    return addPermissionForUser(player, permission, context, lifetimeDate)
  }

  override fun addPermissionForUser(
    player: UUID,
    permission: String,
    context: Context,
    expireAt: Instant
  ): AssignedPermission {
    val id = connection.executeUpdateAndGetId(
      InsertPlayerPermissionForContext(player, context, permission, expireAt)
    )
    return AssignedPermission(
      id,
      context,
      permission,
      expireAt
    )
  }

  override fun removePermissionForUser(id: Int) {
    connection.executeUpdate(RemovePlayerPermissionById(id))
  }

  override fun removePermissionForUser(permission: AssignedPermission) {
    removePermissionForUser(permission.id)
  }

  override fun addPermissionGroupForUser(player: UUID, permissionGroup: PermissionGroup): AssignedGroup {
    connection.executeUpdateAndGetId(InsertGroupForPlayer(player, permissionGroup.id, Context.ALL, lifetimeDate))
      .let { id ->
        return AssignedGroup(
          id,
          permissionGroup.context,
          lifetimeDate,
          permissionGroup
        )
      }
  }

  override fun addPermissionGroupForUser(
    player: UUID,
    permissionGroup: PermissionGroup,
    context: Context
  ): AssignedGroup {
    return addPermissionGroupForUser(player, permissionGroup, lifetimeDate)
  }

  override fun addPermissionGroupForUser(
    player: UUID,
    permissionGroup: PermissionGroup,
    expireAt: Instant
  ): AssignedGroup {
    connection.executeUpdateAndGetId(InsertGroupForPlayer(player, permissionGroup.id, permissionGroup.context, expireAt))
      .let { id ->
        return AssignedGroup(
          id,
          permissionGroup.context,
          expireAt,
          permissionGroup
        )
      }
  }

  override fun removePermissionGroupForUser(player: UUID, assignedGroup: AssignedGroup): PermissionUser {
    connection.executeUpdate(RemovePlayerPermissionById(assignedGroup.id))
    return getPermissionUser(player, assignedGroup.context)
  }

  override fun getGroupHistory(player: UUID, context: Context): List<GroupHistoryEntry> {
    val combined = mutableListOf<GroupHistoryEntry>()
    combined.addAll(connection.executeQuery(SelectHistoryFromHistoryTable(player, context)))
    combined.addAll(connection.executeQuery(SelectHistoryFromCurrentTable(player, context)))
    return combined
  }
}