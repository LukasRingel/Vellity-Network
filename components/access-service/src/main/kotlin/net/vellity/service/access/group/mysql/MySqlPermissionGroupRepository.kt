package net.vellity.service.access.group.mysql

import net.vellity.service.access.group.GroupPermission
import net.vellity.service.access.group.PermissionGroup
import net.vellity.service.access.group.PermissionGroupMetaData
import net.vellity.service.access.group.PermissionGroupRepository
import net.vellity.service.access.group.mysql.group.*
import net.vellity.service.access.group.mysql.metadata.InsertMetaDataForGroup
import net.vellity.service.access.group.mysql.metadata.RemoveMetaDataForGroup
import net.vellity.service.access.group.mysql.metadata.SelectMetaDataForGroup
import net.vellity.service.access.group.mysql.metadata.SelectMetaDataForGroups
import net.vellity.service.access.group.mysql.permission.InsertPermissionForGroup
import net.vellity.service.access.group.mysql.permission.RemovePermissionForGroup
import net.vellity.service.access.group.mysql.permission.SelectPermissionsForGroup
import net.vellity.service.access.group.mysql.permission.SelectPermissionsForGroups
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.query.SelectQuery
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class MySqlPermissionGroupRepository(private val connection: MySqlConnection) : PermissionGroupRepository {
  override fun getPermissionGroups(): List<PermissionGroup> {
    return loadGroupsByQueryAndPermissionsAndMetaDataForGroups(SelectAllPermissionGroups())
  }

  override fun getPermissionGroup(id: Int): PermissionGroup {
    val permissionGroup = connection.executeQuery(SelectGroupById(id)).first()
    connection.executeQuery(SelectMetaDataForGroup(id)).let { permissionGroup.metaData = it }
    connection.executeQuery(SelectPermissionsForGroup(id)).let { permissionGroup.permissions = it }
    return permissionGroup
  }

  override fun getPermissionGroups(context: Context): List<PermissionGroup> {
    return loadGroupsByQueryAndPermissionsAndMetaDataForGroups(SelectGroupsInContext(context))
  }

  override fun createPermissionGroup(context: Context, name: String, activeUntil: Instant): PermissionGroup {
    connection.executeUpdateAndGetId(InsertGroup(context, name, activeUntil)).let {
      return PermissionGroup(it, context, name, mutableListOf(), mutableListOf())
    }
  }

  override fun removePermissionForGroup(permissionGroup: PermissionGroup, permission: GroupPermission) {
    connection.executeUpdate(RemovePermissionForGroup(permission))
  }

  override fun addPermissionForGroup(
    permissionGroup: PermissionGroup,
    permission: String,
    activeUntil: Instant
  ): GroupPermission {
    connection.executeUpdateAndGetId(InsertPermissionForGroup(permissionGroup, permission, activeUntil)).let {
      return GroupPermission(it, permissionGroup.id, permission)
    }
  }

  override fun removeMetaDataForGroup(permissionGroup: PermissionGroup, metaData: PermissionGroupMetaData) {
    connection.executeUpdate(RemoveMetaDataForGroup(metaData))
  }

  override fun addMetaDataForGroup(
    permissionGroup: PermissionGroup,
    metaData: Pair<String, String>
  ): PermissionGroupMetaData {
    connection.executeUpdateAndGetId(InsertMetaDataForGroup(permissionGroup, metaData)).let {
      return PermissionGroupMetaData(it, permissionGroup.id, metaData.first, metaData.second)
    }
  }

  override fun existsGroupWithSameNameInSameContext(context: Context, name: String): Boolean {
    return connection.hasResults(GroupExistsByContextAndName(context, name))
  }

  override fun deletePermissionGroup(id: Int) {
    connection.executeUpdate(DeleteGroupById(id))
  }

  private fun loadGroupsByQueryAndPermissionsAndMetaDataForGroups(query: SelectQuery<PermissionGroup>): List<PermissionGroup> {
    val permissionGroups = connection.executeQuery(query)
    val permissions = connection.executeQuery(SelectPermissionsForGroups(permissionGroups.map { it.id }))
    val metaData = connection.executeQuery(SelectMetaDataForGroups(permissionGroups.map { it.id }))
    for (permissionGroup in permissionGroups) {
      permissionGroup.permissions = permissions.filter { it.groupId == permissionGroup.id }.toMutableList()
      permissionGroup.metaData = metaData.filter { it.groupId == permissionGroup.id }.toMutableList()
    }
    return permissionGroups
  }
}