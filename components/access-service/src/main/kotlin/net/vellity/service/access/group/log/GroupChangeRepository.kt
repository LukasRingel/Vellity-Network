package net.vellity.service.access.group.log

import net.vellity.service.access.group.log.model.GroupMetaDataChange
import net.vellity.service.access.group.log.model.GroupPermissionChange

interface GroupChangeRepository {
  fun logMetaDataChange(change: GroupMetaDataChange)

  fun logPermissionChange(change: GroupPermissionChange)
}