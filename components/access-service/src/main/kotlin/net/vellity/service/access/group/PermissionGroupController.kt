package net.vellity.service.access.group

import net.vellity.service.access.group.blueprint.*
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface PermissionGroupController {
  @GetMapping("/groups/all")
  fun getPermissionGroups(): ResponseEntity<List<PermissionGroup>>

  @GetMapping("/groups/find/id")
  fun getPermissionGroupById(@RequestParam("groupId") groupId: Int): ResponseEntity<PermissionGroup>

  @GetMapping("/groups/find/context")
  fun getPermissionGroupInContext(@RequestParam("context") context: Context): ResponseEntity<List<PermissionGroup>>

  @PostMapping("/groups")
  fun createPermissionGroup(@RequestBody blueprint: PermissionGroupCreationBlueprint): ResponseEntity<PermissionGroup>

  @DeleteMapping("/groups")
  fun deletePermissionGroup(@RequestBody blueprint: PermissionGroupDeleteBlueprint): ResponseEntity<PermissionGroup>

  @PostMapping("/groups/metadata")
  fun addMetaData(@RequestBody blueprint: PermissionGroupMetaDataAddBlueprint): ResponseEntity<PermissionGroupMetaData>

  @DeleteMapping("/groups/metadata")
  fun removeMetaData(@RequestBody blueprint: PermissionGroupMetaDataRemoveBlueprint): ResponseEntity<PermissionGroup>

  @PostMapping("/groups/permission")
  fun addPermission(@RequestBody blueprint: PermissionGroupPermissionAddBlueprint): ResponseEntity<GroupPermission>

  @DeleteMapping("/groups/permission")
  fun removePermissions(@RequestBody blueprint: PermissionGroupPermissionRemoveBlueprint): ResponseEntity<PermissionGroup>
}