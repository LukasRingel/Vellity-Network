package net.vellity.service.usercache

import net.vellity.service.usercache.message.Message
import net.vellity.service.usercache.message.MessageSentStatus
import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface MessagesRequests {
  @GET("/messages")
  fun getMessage(
    @Query("id") id: Int
  ): Call<Message>

  @PUT("/messages/bulk")
  fun getMessages(
    @Body ids: List<Int>
  ): Call<List<Message>>

  @POST("/messages")
  fun saveMessage(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("message") message: String,
    @Query("status") status: MessageSentStatus
  ): Call<Unit>

  @GET("/messages/last")
  fun getLastMessages(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("limit") limit: Int
  ): Call<List<Message>>

  @GET("/messages/between")
  fun getMessagesBetween(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("startDate") startDate: String,
    @Query("endDate") endDate: String,
  ): Call<List<Message>>
}