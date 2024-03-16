package net.vellity.service.access.user

import net.vellity.utils.context.Context
import java.util.*

data class PermissionUser(
  val player: UUID,
  var groups: List<AssignedGroup>,
  var permissions: List<AssignedPermission>
) {
  fun hasPermission(permission: String): Boolean {
    return permissions.any { it.permission == permission } || groups.any { it.group.hasPermission(permission) }
  }

  fun groups(context: Context): List<AssignedGroup> {
    return groups.filter { it.context == context }
  }
}
