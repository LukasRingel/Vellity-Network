package net.vellity.service.access.user

import net.vellity.service.access.group.PermissionGroupService
import net.vellity.service.access.user.blueprint.PermissionUserGroupAddBlueprint
import net.vellity.service.access.user.blueprint.PermissionUserGroupRemoveBlueprint
import net.vellity.service.access.user.blueprint.PermissionUserPermissionAddBlueprint
import net.vellity.service.access.user.blueprint.PermissionUserPermissionRemoveBlueprint
import net.vellity.service.access.user.log.UserChangeLogService
import net.vellity.service.access.user.log.model.UserGroupChange
import net.vellity.service.access.user.log.model.UserPermissionChange
import net.vellity.utils.configuration.lifetimeDate
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
class DirectPermissionUserController(
  private val repository: PermissionUserRepository,
  private val groupService: PermissionGroupService,
  private val changeLogService: UserChangeLogService
) : PermissionUserController {
  override fun findUser(playerId: UUID): ResponseEntity<PermissionUser> {
    return ResponseEntity.ok(repository.getPermissionUser(playerId))
  }

  override fun findUserWithContext(playerId: UUID, context: Context): ResponseEntity<PermissionUser> {
    return ResponseEntity.ok(repository.getPermissionUser(playerId, context))
  }

  override fun addPermission(blueprint: PermissionUserPermissionAddBlueprint): ResponseEntity<AssignedPermission> {
    changeLogService.logPermissionChange(UserPermissionChange.fromAddBlueprint(blueprint))
    return ResponseEntity.ok(
      repository.addPermissionForUser(
        blueprint.playerId,
        blueprint.permission,
        blueprint.context,
        if (blueprint.expireAt != 0L) {
          Instant.ofEpochMilli(blueprint.expireAt)
        } else {
          lifetimeDate
        }
      )
    )
  }

  override fun removePermission(blueprint: PermissionUserPermissionRemoveBlueprint): ResponseEntity<PermissionUser> {
    repository.getPermissionUser(
      blueprint.playerId,
      blueprint.context
    ).permissions.first { it.permission == blueprint.permission }
      .let { assignedPermission ->
        repository.removePermissionForUser(assignedPermission)
        changeLogService.logPermissionChange(
          UserPermissionChange.fromAssignedPermissionAndUuid(
            blueprint.playerId,
            assignedPermission
          )
        )
      }
    return ResponseEntity.ok(repository.getPermissionUser(blueprint.playerId))
  }

  override fun addGroup(blueprint: PermissionUserGroupAddBlueprint): ResponseEntity<AssignedGroup> {
    groupService.getPermissionGroupById(blueprint.group).let { group ->

      if (group == null) {
        return ResponseEntity.internalServerError().build()
      }

      changeLogService.logGroupChange(UserGroupChange.fromAddBlueprint(blueprint))

      return ResponseEntity.ok(
        repository.addPermissionGroupForUser(
          blueprint.playerId,
          group,
          if (blueprint.expireAt != 0L) {
            Instant.ofEpochMilli(blueprint.expireAt)
          } else {
            lifetimeDate
          }
        )
      )
    }
  }

  override fun removeGroup(blueprint: PermissionUserGroupRemoveBlueprint): ResponseEntity<PermissionUser> {
    repository.getPermissionUser(blueprint.playerId).let { user ->
      user.groups.find { it.id == blueprint.assignedGroupId }?.let { group ->
        repository.removePermissionGroupForUser(blueprint.playerId, group)
        changeLogService.logGroupChange(UserGroupChange.fromRemoveBlueprint(group.group, blueprint))
      }
    }
    return ResponseEntity.ok(repository.getPermissionUser(blueprint.playerId))
  }

  override fun getGroupHistory(playerId: UUID, context: Context): ResponseEntity<List<GroupHistoryEntry>> {
    return ResponseEntity.ok(repository.getGroupHistory(playerId, context))
  }

  override fun hasPermission(playerId: UUID, context: Context, permission: String): ResponseEntity<Boolean> {
    repository.getPermissionUser(playerId, context).let { user ->
      if (user.permissions.any { it.permission == permission || it.permission == "*" }) {
        return ResponseEntity.ok(true)
      }
      if (user.groups.any { pit -> pit.group.permissions.any { it.name == permission || it.name == "*" } }) {
        return ResponseEntity.ok(true)
      }
      return ResponseEntity.ok(false)
    }
  }
}