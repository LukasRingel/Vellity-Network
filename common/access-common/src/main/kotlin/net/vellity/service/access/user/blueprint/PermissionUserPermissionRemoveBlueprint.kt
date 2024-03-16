package net.vellity.service.access.user.blueprint

import net.vellity.utils.context.Context
import java.util.UUID

data class PermissionUserPermissionRemoveBlueprint(
  val playerId: UUID,
  val context: Context,
  val permission: String
)
