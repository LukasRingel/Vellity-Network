package net.vellity.service.friends

import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface ProfileRequests {
  @GET("/profile")
  fun getFriendProfile(
    @Query("context") context: Context,
    @Query("player") player: UUID
  ): Call<FriendProfile>
}