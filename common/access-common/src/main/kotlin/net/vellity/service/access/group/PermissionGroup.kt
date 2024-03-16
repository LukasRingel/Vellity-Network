package net.vellity.service.access.group

import net.vellity.utils.context.Context

data class PermissionGroup(
  val id: Int,
  val context: Context,
  val name: String,
  var permissions: List<GroupPermission>,
  var metaData: List<PermissionGroupMetaData>
) {
  fun hasPermission(permission: String): Boolean {
    return permissions.any { it.name == permission }
  }

  fun isForContext(context: Context): Boolean {
    return this.context == context
  }

  fun getMetaDataValue(key: String): String? {
    return metaData.firstOrNull { it.name == key }?.value
  }

  fun getMetaDataValue(key: String, defaultValue: String): String {
    return getMetaDataValue(key) ?: defaultValue
  }

  fun hasMetaData(key: String): Boolean {
    return metaData.any { it.name == key }
  }

  fun hasMetaData(key: String, value: String): Boolean {
    return metaData.any { it.name == key && it.value == value }
  }
}