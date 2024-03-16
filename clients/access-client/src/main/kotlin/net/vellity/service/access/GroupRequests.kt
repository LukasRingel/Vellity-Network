package net.vellity.service.access

import retrofit2.Call
import net.vellity.service.access.group.GroupPermission
import net.vellity.service.access.group.PermissionGroup
import net.vellity.service.access.group.PermissionGroupMetaData
import net.vellity.service.access.group.blueprint.*
import net.vellity.utils.context.Context
import retrofit2.http.*

interface GroupRequests {
  @GET("/groups/all")
  fun allGroups(): Call<List<PermissionGroup>>

  @GET("/groups/find/id")
  fun groupWithId(@Query("groupId") groupId: Int): Call<PermissionGroup>

  @GET("/groups/find/context")
  fun groupsInContext(@Query("context") context: Context): Call<List<PermissionGroup>>

  @POST("/groups")
  fun createGroup(@Body blueprint: PermissionGroupCreationBlueprint): Call<PermissionGroup>

  @DELETE("/groups")
  fun deleteGroup(@Body blueprint: PermissionGroupDeleteBlueprint): Call<PermissionGroup>

  @POST("/groups/metadata")
  fun addMetaData(@Body blueprint: PermissionGroupMetaDataAddBlueprint): Call<PermissionGroupMetaData>

  @DELETE("/groups/metadata")
  fun removeMetaData(@Body blueprint: PermissionGroupMetaDataRemoveBlueprint): Call<PermissionGroup>

  @POST("/groups/permission")
  fun addPermission(@Body blueprint: PermissionGroupPermissionAddBlueprint): Call<GroupPermission>

  @DELETE("/groups/permission")
  fun removePermissions(@Body blueprint: PermissionGroupPermissionRemoveBlueprint): Call<PermissionGroup>
}