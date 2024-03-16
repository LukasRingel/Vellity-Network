package net.vellity.service.usercache

import net.vellity.service.usercache.login.PlayerLogin
import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface LoginRequests {
  @GET("/logins")
  fun getLastLogins(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("limit") limit: Int
  ): Call<List<PlayerLogin>>

  @POST("/logins")
  fun registerLogin(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("address") address: String
  ): Call<Unit>

  @POST("/logout")
  fun registerLogout(
    @Query("context") context: Context,
    @Query("player") player: UUID
  ): Call<Unit>
}