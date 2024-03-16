package net.vellity.service.access.group.log.model

import net.vellity.service.access.group.PermissionGroup
import net.vellity.service.access.group.blueprint.PermissionGroupMetaDataAddBlueprint
import net.vellity.service.access.group.blueprint.PermissionGroupMetaDataRemoveBlueprint

data class GroupMetaDataChange(
  val group: Int,
  val metaData: String,
  val oldValue: String,
  val newValue: String
) {
  companion object {
    fun fromAddBlueprint(permissionGroupById: PermissionGroup, blueprint: PermissionGroupMetaDataAddBlueprint):
      GroupMetaDataChange =
      GroupMetaDataChange(
        permissionGroupById.id,
        blueprint.metaData.first,
        permissionGroupById.getMetaDataValue(blueprint.metaData.first) ?: "",
        blueprint.metaData.second
      )

    fun fromRemoveBlueprint(permissionGroupById: PermissionGroup, blueprint: PermissionGroupMetaDataRemoveBlueprint):
      GroupMetaDataChange =
      GroupMetaDataChange(
        permissionGroupById.id,
        blueprint.metaDataKey,
        permissionGroupById.getMetaDataValue(blueprint.metaDataKey) ?: "",
        ""
      )
  }
}