package net.vellity.service.access.group

import net.vellity.utils.context.Context
import java.time.Instant

interface PermissionGroupRepository {

  fun getPermissionGroups(): List<PermissionGroup>

  fun getPermissionGroup(id: Int): PermissionGroup?

  fun getPermissionGroups(context: Context): List<PermissionGroup>

  fun createPermissionGroup(context: Context, name: String, activeUntil: Instant): PermissionGroup

  fun removePermissionForGroup(permissionGroup: PermissionGroup, permission: GroupPermission)

  fun addPermissionForGroup(permissionGroup: PermissionGroup, permission: String, activeUntil: Instant) : GroupPermission

  fun removeMetaDataForGroup(permissionGroup: PermissionGroup, metaData: PermissionGroupMetaData)

  fun addMetaDataForGroup(permissionGroup: PermissionGroup, metaData: Pair<String, String>) : PermissionGroupMetaData

  fun existsGroupWithSameNameInSameContext(context: Context, name: String): Boolean

  fun deletePermissionGroup(id: Int)

}