package net.vellity.service.access.group.log.model

import net.vellity.service.access.group.PermissionGroup
import net.vellity.service.access.group.blueprint.PermissionGroupPermissionAddBlueprint
import net.vellity.service.access.group.blueprint.PermissionGroupPermissionRemoveBlueprint

data class GroupPermissionChange(
  val group: Int,
  val permission: String,
  val oldValue: Boolean,
  val newValue: Boolean
) {
  companion object {
    fun fromAddBlueprint(permissionGroupById: PermissionGroup, blueprint: PermissionGroupPermissionAddBlueprint):
      GroupPermissionChange =
      GroupPermissionChange(
        permissionGroupById.id,
        blueprint.permission,
        oldValue = false,
        newValue = true
      )

    fun fromRemoveBlueprint(permissionGroupById: PermissionGroup, blueprint: PermissionGroupPermissionRemoveBlueprint):
      GroupPermissionChange =
      GroupPermissionChange(
        permissionGroupById.id,
        blueprint.permission,
        oldValue = true,
        newValue = false
      )
  }
}