package net.vellity.service.access.user

import net.vellity.utils.context.Context
import java.time.Instant

data class AssignedPermission(
  val id: Int,
  val context: Context,
  val permission: String,
  val expireAt: Instant
)
