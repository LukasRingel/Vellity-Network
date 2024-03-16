package net.vellity.service.usercache

import net.vellity.service.usercache.combined.TextureAndName
import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Query
import java.util.*

interface CombinedRequests {
  @PUT("/combined/login")
  fun handleLoginWithDetails(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("name") name: String,
    @Query("proxyServer") proxyServer: String,
    @Query("ip") ip: String,
    @Query("textureSignature") textureSignature: String,
    @Query("textureValue") textureValue: String
  ): Call<Unit>

  @PUT("/combined/texturesAndName")
  fun bulkGetTexturesAndName(
    @Body players: List<UUID>
  ): Call<List<TextureAndName>>
}