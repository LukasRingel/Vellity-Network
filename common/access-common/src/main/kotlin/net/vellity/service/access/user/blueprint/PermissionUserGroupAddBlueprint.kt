package net.vellity.service.access.user.blueprint

import java.util.*

data class PermissionUserGroupAddBlueprint(
  val playerId: UUID,
  val group: Int,
  var expireAt: Long = 0
)