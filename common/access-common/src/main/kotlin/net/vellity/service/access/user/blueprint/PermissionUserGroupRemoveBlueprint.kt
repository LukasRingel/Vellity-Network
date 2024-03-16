package net.vellity.service.access.user.blueprint

import java.util.*

data class PermissionUserGroupRemoveBlueprint(
  val playerId: UUID,
  val assignedGroupId: Int
)
