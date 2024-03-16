package net.vellity.service.usercache

import net.vellity.service.usercache.notification.Notification
import net.vellity.service.usercache.notification.ToggledNotification
import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query
import java.util.*

interface NotificationsRequests {
  @GET("/notifications")
  fun getToggledNotification(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("notification") notification: Notification
  ): Call<ToggledNotification?>

  @GET("/notifications/all")
  fun getAllNotifications(
    @Query("context") context: Context,
    @Query("player") player: UUID
  ): Call<List<ToggledNotification>>

  @PUT("/notifications")
  fun updateNotification(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("notification") notification: Notification,
    @Query("enabled") enabled: Boolean
  ): Call<ToggledNotification>
}