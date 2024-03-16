package net.vellity.service.access.user.blueprint

import net.vellity.utils.context.Context
import java.util.*

data class PermissionUserPermissionAddBlueprint(
  val playerId: UUID,
  val context: Context,
  val permission: String,
  var expireAt: Long = 0
)