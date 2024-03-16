package net.vellity.service.guardian

import net.vellity.service.guardian.chatlog.Chatlog
import net.vellity.service.guardian.chatlog.ChatlogMessage
import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

interface ChatlogRequests {
  @GET("/chatlog")
  fun getChatLog(
    @Query("id") id: Int
  ): Call<Chatlog>

  @GET("/chatlog/messages")
  fun getMessagesForChatlog(
    @Query("id") id: Int
  ): Call<List<ChatlogMessage>>

  @POST("/chatlog")
  fun createChatLog(
    @Query("context") context: Context,
    @Query("creator") creator: UUID,
    @Query("target") target: UUID
  ): Call<Chatlog>

  @DELETE("/chatlog")
  fun deleteChatLog(
    @Query("id") id: Int
  ): Call<Unit>

  @GET("/chatlog/target")
  fun getChatLogByTarget(
    @Query("context") context: Context,
    @Query("target") target: UUID
  ): Call<List<Chatlog>>
}