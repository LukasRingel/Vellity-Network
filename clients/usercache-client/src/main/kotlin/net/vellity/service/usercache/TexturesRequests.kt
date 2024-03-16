package net.vellity.service.usercache

import net.vellity.service.usercache.texture.PlayerTexture
import net.vellity.service.usercache.texture.PlayerTextureHistoryEntry
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface TexturesRequests {
  @GET("/textures/current")
  fun getTextureByUuid(@Query("player") player: UUID): Call<PlayerTexture>

  @GET("/textures/history")
  fun getTextureHistory(@Query("player") player: UUID): Call<List<PlayerTextureHistoryEntry>>

  @GET("/textures/current/bulk")
  fun getTexturesForUuids(@Body players: List<UUID>): Call<List<PlayerTexture>>

  @POST("/textures/check")
  fun checkForTextureChange(
    @Query("player") player: UUID,
    @Query("signature") signature: String,
    @Query("texture") value: String
  ): Call<Unit>
}