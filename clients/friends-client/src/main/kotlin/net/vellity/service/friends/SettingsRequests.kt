package net.vellity.service.friends

import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

interface SettingsRequests {
  @GET("/settings")
  fun getSettings(
    @Query("context") context: Context,
    @Query("player") player: UUID
  ): Call<Map<Setting, SettingState>>

  @GET("/settings/check")
  fun checkSetting(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("setting") setting: Setting,
    @Query("target") target: UUID,
    @Query("knowIfFriends") knowIfFriends: Boolean
  ): Call<Boolean>

  @POST("/settings")
  fun updateSetting(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("setting") setting: Setting,
    @Query("value") value: SettingState
  ): Call<Unit>
}