package net.vellity.service.access.user

import net.vellity.service.access.group.PermissionGroup
import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

interface PermissionUserRepository {

  fun getPermissionUser(player: UUID): PermissionUser

  fun getPermissionUser(player: UUID, context: Context): PermissionUser

  fun addPermissionForUser(player: UUID, permission: String): AssignedPermission

  fun addPermissionForUser(player: UUID, permission: String, context: Context): AssignedPermission

  fun addPermissionForUser(player: UUID, permission: String, context: Context, expireAt: Instant): AssignedPermission

  fun removePermissionForUser(id: Int)

  fun removePermissionForUser(permission: AssignedPermission)

  fun addPermissionGroupForUser(player: UUID, permissionGroup: PermissionGroup): AssignedGroup

  fun addPermissionGroupForUser(player: UUID, permissionGroup: PermissionGroup, context: Context): AssignedGroup

  fun addPermissionGroupForUser(
    player: UUID,
    permissionGroup: PermissionGroup,
    expireAt: Instant
  ): AssignedGroup

  fun removePermissionGroupForUser(player: UUID, assignedGroup: AssignedGroup): PermissionUser

  fun getGroupHistory(player: UUID, context: Context): List<GroupHistoryEntry>
}