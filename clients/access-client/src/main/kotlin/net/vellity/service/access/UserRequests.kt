package net.vellity.service.access

import net.vellity.service.access.user.AssignedGroup
import net.vellity.service.access.user.AssignedPermission
import net.vellity.service.access.user.PermissionUser
import net.vellity.service.access.user.blueprint.PermissionUserGroupAddBlueprint
import net.vellity.service.access.user.blueprint.PermissionUserGroupRemoveBlueprint
import net.vellity.service.access.user.blueprint.PermissionUserPermissionAddBlueprint
import net.vellity.service.access.user.blueprint.PermissionUserPermissionRemoveBlueprint
import net.vellity.utils.context.Context
import retrofit2.http.*
import java.util.*
import retrofit2.Call

interface UserRequests {
  @GET("/user/find")
  fun get(@Query("playerId") playerId: UUID): Call<PermissionUser>

  @GET("/user/find/context")
  fun get(@Query("playerId") playerId: UUID, @Query("context") context: Context): Call<PermissionUser>

  @POST("/user/permissions")
  fun addPermission(@Body blueprint: PermissionUserPermissionAddBlueprint): Call<AssignedPermission>

  @DELETE("/user/permissions")
  fun removePermission(@Body blueprint: PermissionUserPermissionRemoveBlueprint): Call<PermissionUser>

  @POST("/user/group")
  fun addGroup(@Body blueprint: PermissionUserGroupAddBlueprint): Call<AssignedGroup>

  @DELETE("/user/group")
  fun removeGroup(@Body blueprint: PermissionUserGroupRemoveBlueprint): Call<PermissionUser>

  @GET("user/check")
  fun hasPermission(
    @Query("playerId") playerId: UUID,
    @Query("context") context: Context,
    @Query("permission") permission: String
  ): Call<Boolean>
}