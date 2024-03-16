package net.vellity.service.access.user.mysql.group

import net.vellity.service.access.group.PermissionGroupService
import net.vellity.service.access.user.AssignedGroup
import net.vellity.utils.context.Context
import java.time.Instant

data class AssignedGroupRepresentation(
  val id: Int,
  val context: Context,
  val expireAt: Instant,
  val groupId: Int
) {
  fun toAssignedGroup(groupService: PermissionGroupService): AssignedGroup? {
    return groupService.getPermissionGroupById(groupId)?.let {
      return AssignedGroup(
        id,
        context,
        expireAt,
        it
      )
    }
  }
}
