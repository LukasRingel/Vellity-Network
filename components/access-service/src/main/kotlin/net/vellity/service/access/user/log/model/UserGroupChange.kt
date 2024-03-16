package net.vellity.service.access.user.log.model

import net.vellity.service.access.group.PermissionGroup
import net.vellity.service.access.user.blueprint.PermissionUserGroupAddBlueprint
import net.vellity.service.access.user.blueprint.PermissionUserGroupRemoveBlueprint
import net.vellity.utils.configuration.lifetimeDate
import net.vellity.utils.mysql.extensions.nowUtc
import java.time.Instant
import java.util.*

data class UserGroupChange(
  val player: UUID,
  val group: Int,
  val expireAt: Instant,
  val oldValue: Boolean,
  val newValue: Boolean
) {
  companion object {
    fun fromAddBlueprint(blueprint: PermissionUserGroupAddBlueprint):
      UserGroupChange =
      UserGroupChange(
        blueprint.playerId,
        blueprint.group,
        if (blueprint.expireAt == 0L) lifetimeDate else Instant.ofEpochMilli(blueprint.expireAt),
        oldValue = false,
        newValue = true
      )

    fun fromRemoveBlueprint(permissionGroupById: PermissionGroup, blueprint: PermissionUserGroupRemoveBlueprint):
      UserGroupChange =
      UserGroupChange(
        blueprint.playerId,
        permissionGroupById.id,
        nowUtc(),
        oldValue = true,
        newValue = false
      )
  }
}