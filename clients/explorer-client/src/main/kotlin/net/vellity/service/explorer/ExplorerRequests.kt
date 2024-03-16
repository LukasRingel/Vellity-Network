package net.vellity.service.explorer

import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ExplorerRequests {
  @GET("/registry")
  fun getAll(
    @Query("context") context: Context
  ): Call<List<Identity>>

  @GET("/registry/filter")
  fun getAllOfType(
    @Query("type") type: String,
    @Query("context") context: Context
  ): Call<List<Identity>>

  @POST("/registry")
  fun register(
    @Query("type") type: String,
    @Query("port") port: Int,
    @Query("address") address: String,
    @Query("context") context: Context
  ): Call<Identity>

  @PUT("/registry")
  fun updateHearthBeat(
    @Query("type") type: String,
    @Query("id") id: String,
    @Query("context") context: Context,
    @Query("port") port: Int,
    @Query("address") address: String,
  ): Call<Unit>

  @DELETE("/registry")
  fun unregister(
    @Query("type") type: String,
    @Query("id") id: String,
    @Query("context") context: Context
  ): Call<Unit>
}