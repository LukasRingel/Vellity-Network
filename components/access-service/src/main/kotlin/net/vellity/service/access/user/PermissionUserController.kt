package net.vellity.service.access.user

import net.vellity.service.access.user.blueprint.PermissionUserGroupAddBlueprint
import net.vellity.service.access.user.blueprint.PermissionUserGroupRemoveBlueprint
import net.vellity.service.access.user.blueprint.PermissionUserPermissionAddBlueprint
import net.vellity.service.access.user.blueprint.PermissionUserPermissionRemoveBlueprint
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

interface PermissionUserController {
  @GetMapping("/user/find")
  fun findUser(@RequestParam("playerId") playerId: UUID): ResponseEntity<PermissionUser>

  @GetMapping("/user/find/context")
  fun findUserWithContext(
    @RequestParam("playerId") playerId: UUID,
    @RequestParam("context") context: Context
  ): ResponseEntity<PermissionUser>

  @PostMapping("/user/permissions")
  fun addPermission(@RequestBody blueprint: PermissionUserPermissionAddBlueprint): ResponseEntity<AssignedPermission>

  @DeleteMapping("/user/permissions")
  fun removePermission(@RequestBody blueprint: PermissionUserPermissionRemoveBlueprint): ResponseEntity<PermissionUser>

  @PostMapping("/user/group")
  fun addGroup(@RequestBody blueprint: PermissionUserGroupAddBlueprint): ResponseEntity<AssignedGroup>

  @DeleteMapping("/user/group")
  fun removeGroup(@RequestBody blueprint: PermissionUserGroupRemoveBlueprint): ResponseEntity<PermissionUser>

  @GetMapping("/user/group/history")
  fun getGroupHistory(
    @RequestParam("playerId") playerId: UUID,
    @RequestParam("context") context: Context
  ): ResponseEntity<List<GroupHistoryEntry>>

  @GetMapping("user/check")
  fun hasPermission(
    @RequestParam("playerId") playerId: UUID,
    @RequestParam("context") context: Context,
    @RequestParam("permission") permission: String
  ): ResponseEntity<Boolean>
}