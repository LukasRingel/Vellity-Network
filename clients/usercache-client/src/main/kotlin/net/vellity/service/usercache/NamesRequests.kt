package net.vellity.service.usercache

import net.vellity.service.usercache.name.NameHistoryEntry
import net.vellity.service.usercache.name.NameResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import java.util.*

interface NamesRequests {
  @GET("/names/current")
  fun getNameByUuid(@Query("player") player: UUID): Call<NameResponse>

  @GET("/names/history")
  fun getNameHistory(@Query("player") player: UUID): Call<List<NameHistoryEntry>>

  @PUT("/names/current/bulk")
  fun getNamesByUuids(@Body players: List<String>): Call<List<NameResponse>>

  @GET("/names/current/uuid")
  fun getUuidByName(@Query("name") name: String): Call<UUID>

  @POST("/names/check")
  fun checkForNameChange(@Query("player") player: UUID, @Query("name") name: String): Call<Unit>
}