package net.vellity.service.config

import net.vellity.utils.context.Context
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.time.Instant
import retrofit2.Call

interface FeatureFlagRequests {
  @GET("/flags")
  fun getFlags(@Query("context") context: Context): Call<List<FeatureFlag>>

  @GET("/flags/id")
  fun getFlag(
    @Query("context") context: Context,
    @Query("name") name: String
  ): Call<FeatureFlag>

  @GET("/flags/active")
  fun isFlagActive(
    @Query("context") context: Context,
    @Query("name") name: String,
    @Query("default") default: Boolean
  ): Call<Boolean>

  @POST("/flags")
  fun createFlag(
    @Query("context") context: Context,
    @Query("name") name: String,
    @Query("enabled") enabled: Boolean,
    @Query("activeUntil") activeUntil: Instant
  ): Call<FeatureFlag>

  @POST("/flags")
  fun updateFlag(
    @Query("context") context: Context,
    @Query("name") name: String,
    @Query("enabled") enabled: Boolean,
    @Query("activeUntil") activeUntil: Instant
  ): Call<FeatureFlag>
}