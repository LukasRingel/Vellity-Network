package net.vellity.service.access.user.log

import net.vellity.service.access.user.log.model.UserGroupChange
import net.vellity.service.access.user.log.model.UserPermissionChange

interface UserChangeLogService {
  fun logGroupChange(change: UserGroupChange)

  fun logPermissionChange(change: UserPermissionChange)
}