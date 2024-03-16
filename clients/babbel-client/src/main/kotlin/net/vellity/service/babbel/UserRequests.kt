package net.vellity.service.babbel

import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

interface UserRequests {
  @GET("/user")
  fun getUser(
    @Query("context") context: Context,
    @Query("playerId") playerId: UUID
  ): Call<Locale>

  @POST("/user")
  fun updateLanguage(
    @Query("context") context: Context,
    @Query("playerId") playerId: UUID,
    @Query("language") language: String
  ): Call<Unit>
}