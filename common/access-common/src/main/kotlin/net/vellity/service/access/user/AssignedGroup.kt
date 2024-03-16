package net.vellity.service.access.user

import net.vellity.service.access.group.PermissionGroup
import net.vellity.utils.context.Context
import java.time.Instant

data class AssignedGroup(
  val id: Int,
  val context: Context,
  val expireAt: Instant,
  val group: PermissionGroup
)
