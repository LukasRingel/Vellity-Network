package net.vellity.service.access.user.log.model

import net.vellity.service.access.user.AssignedPermission
import net.vellity.service.access.user.blueprint.PermissionUserPermissionAddBlueprint
import net.vellity.utils.configuration.lifetimeDate
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.nowUtc
import java.time.Instant
import java.util.UUID

data class UserPermissionChange(
  val player: UUID,
  val permission: String,
  val context: Context,
  val expireAt: Instant,
  val oldValue: Boolean,
  val newValue: Boolean
) {
  companion object {
    fun fromAddBlueprint(blueprint: PermissionUserPermissionAddBlueprint):
      UserPermissionChange =
      UserPermissionChange(
        blueprint.playerId,
        blueprint.permission,
        blueprint.context,
        if (blueprint.expireAt == 0L) lifetimeDate else Instant.ofEpochMilli(blueprint.expireAt),
        oldValue = false,
        newValue = true
      )

    fun fromAssignedPermissionAndUuid(player: UUID, permission: AssignedPermission):
      UserPermissionChange =
      UserPermissionChange(
        player,
        permission.permission,
        permission.context,
        nowUtc(),
        oldValue = true,
        newValue = false
      )
  }
}