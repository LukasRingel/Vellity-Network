package net.vellity.service.usercache

import net.vellity.service.usercache.session.UserSession
import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface SessionRequests {
  @GET("/sessions")
  fun getCurrentSession(
    @Query("context") context: Context,
    @Query("player") player: UUID
  ): Call<UserSession>

  @GET("/sessions/context")
  fun getCurrentSessionsOnContext(
    @Query("context") context: Context
  ): Call<List<UserSession>>

  @PUT("/sessions/server")
  fun updateCurrentServer(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("server") server: String
  ): Call<Unit>

  @PUT("/sessions/proxy")
  fun updateCurrentProxy(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("proxy") proxy: String
  ): Call<Unit>

  @POST("/sessions/keepalive")
  fun keepSessionsAlive(
    @Query("context") context: Context,
    @Body players: List<UUID>
  ): Call<Unit>
}