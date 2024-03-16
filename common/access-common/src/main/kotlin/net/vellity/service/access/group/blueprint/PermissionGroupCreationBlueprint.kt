package net.vellity.service.access.group.blueprint

import net.vellity.utils.context.Context

data class PermissionGroupCreationBlueprint(
  val context: Context,
  val name: String
)
