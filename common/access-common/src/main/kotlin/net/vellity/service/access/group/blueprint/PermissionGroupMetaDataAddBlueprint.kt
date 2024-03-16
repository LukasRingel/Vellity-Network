package net.vellity.service.access.group.blueprint

data class PermissionGroupMetaDataAddBlueprint(
  val groupId: Int,
  val metaData: Pair<String, String>
)