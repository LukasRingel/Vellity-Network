package net.vellity.service.access.group

import net.vellity.service.access.group.blueprint.*
import net.vellity.service.access.group.log.GroupChangeLogService
import net.vellity.service.access.group.log.model.GroupMetaDataChange
import net.vellity.service.access.group.log.model.GroupPermissionChange
import net.vellity.utils.configuration.lifetimeDate
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ServicePermissionGroupController(
  private val groupService: PermissionGroupService,
  private val changeService: GroupChangeLogService,
) : PermissionGroupController {
  override fun getPermissionGroups(): ResponseEntity<List<PermissionGroup>> {
    return ResponseEntity.ok(groupService.getPermissionGroups())
  }

  override fun getPermissionGroupById(groupId: Int): ResponseEntity<PermissionGroup> {
    return ResponseEntity.ok(groupService.getPermissionGroupById(groupId))
  }

  override fun getPermissionGroupInContext(context: Context): ResponseEntity<List<PermissionGroup>> {
    return ResponseEntity.ok(groupService.getPermissionGroupInContext(context))
  }

  override fun createPermissionGroup(blueprint: PermissionGroupCreationBlueprint): ResponseEntity<PermissionGroup> {
    val permissionGroup = groupService.repository().createPermissionGroup(
      blueprint.context,
      blueprint.name,
      lifetimeDate
    )
    groupService.deployUpdate()
    return ResponseEntity.ok(permissionGroup)
  }

  override fun deletePermissionGroup(blueprint: PermissionGroupDeleteBlueprint): ResponseEntity<PermissionGroup> {
    val permissionGroup = groupService.getPermissionGroupById(blueprint.groupId)
      ?: return ResponseEntity.notFound().build()
    groupService.repository().deletePermissionGroup(permissionGroup.id)
    groupService.deployUpdate()
    return ResponseEntity.ok(permissionGroup)
  }

  override fun addMetaData(blueprint: PermissionGroupMetaDataAddBlueprint): ResponseEntity<PermissionGroupMetaData> {
    val permissionGroupById = groupService.getPermissionGroupById(blueprint.groupId)
      ?: return ResponseEntity.notFound().build()
    val metaData = groupService.repository().addMetaDataForGroup(permissionGroupById, blueprint.metaData)
    groupService.deployUpdate()
    changeService.logMetaDataChange(GroupMetaDataChange.fromAddBlueprint(permissionGroupById, blueprint))
    return ResponseEntity.ok(metaData)
  }

  override fun removeMetaData(blueprint: PermissionGroupMetaDataRemoveBlueprint): ResponseEntity<PermissionGroup> {
    val permissionGroupById =
      groupService.getPermissionGroupById(blueprint.groupId) ?: return ResponseEntity.notFound().build()
    val metaData =
      permissionGroupById.metaData.find { it.name == blueprint.metaDataKey } ?: return ResponseEntity.notFound().build()
    groupService.repository().removeMetaDataForGroup(permissionGroupById, metaData)
    groupService.deployUpdate()
    changeService.logMetaDataChange(GroupMetaDataChange.fromRemoveBlueprint(permissionGroupById, blueprint))
    return ResponseEntity.ok(groupService.repository().getPermissionGroup(blueprint.groupId))
  }

  override fun addPermission(blueprint: PermissionGroupPermissionAddBlueprint): ResponseEntity<GroupPermission> {
    val permissionGroupById = groupService.getPermissionGroupById(blueprint.groupId)
      ?: return ResponseEntity.notFound().build()
    val permission =
      groupService.repository().addPermissionForGroup(permissionGroupById, blueprint.permission, lifetimeDate)
    groupService.deployUpdate()
    changeService.logPermissionChange(GroupPermissionChange.fromAddBlueprint(permissionGroupById, blueprint))
    return ResponseEntity.ok(permission)
  }

  override fun removePermissions(blueprint: PermissionGroupPermissionRemoveBlueprint): ResponseEntity<PermissionGroup> {
    val permissionGroupById = groupService.getPermissionGroupById(blueprint.groupId)
      ?: return ResponseEntity.notFound().build()
    if (!permissionGroupById.hasPermission(blueprint.permission)) {
      return ResponseEntity.notFound().build()
    }
    permissionGroupById.permissions.find { it.name == blueprint.permission }?.let {
      groupService.repository().removePermissionForGroup(permissionGroupById, it)
      groupService.deployUpdate()
    }
    changeService.logPermissionChange(GroupPermissionChange.fromRemoveBlueprint(permissionGroupById, blueprint))
    return ResponseEntity.ok(groupService.repository().getPermissionGroup(blueprint.groupId))
  }
}