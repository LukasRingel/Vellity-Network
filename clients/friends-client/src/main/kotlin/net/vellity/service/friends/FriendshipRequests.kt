package net.vellity.service.friends

import net.vellity.utils.context.Context
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface FriendshipRequests {
  @GET("/friendships")
  fun getFriendships(
    @Query("context") context: Context,
    @Query("player") player: UUID
  ): Call<List<Friendship>>

  @GET("/friendships/friends")
  fun getOnlyFriends(
    @Query("context") context: Context,
    @Query("player") player: UUID
  ): Call<List<Friendship>>

  @GET("/friendships/with")
  fun getFriendshipWith(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("friend") friend: UUID
  ): Call<Friendship>

  @DELETE("/friendships/with")
  fun deleteFriendshipWith(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("friend") friend: UUID
  ): Call<Unit>

  @GET("/friendships/requested")
  fun getRequested(
    @Query("context") context: Context,
    @Query("player") player: UUID
  ): Call<List<FriendRequest>>

  @GET("/friendships/requests")
  fun getRequests(
    @Query("context") context: Context,
    @Query("player") player: UUID
  ): Call<List<FriendRequest>>

  @POST("/friendships/requests")
  fun createFriendshipRequest(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("friend") friend: UUID
  ): Call<FriendRequest>

  @POST("/friendships/requests/accept")
  fun acceptFriendshipRequest(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("friend") friend: UUID
  ): Call<Friendship>

  @POST("/friendships/requests/deny")
  fun denyFriendshipRequest(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("friend") friend: UUID
  ): Call<ResponseBody>

  @DELETE("/friendships/requests")
  fun deleteFriendshipRequest(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("friend") friend: UUID
  ): Call<ResponseBody>

  @POST("/friendships/state")
  fun updateFriendshipState(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("friend") friend: UUID,
    @Query("state") state: FriendshipState
  ): Call<ResponseBody>

  @GET("/friendships/blocklist")
  fun getBlocklist(
    @Query("context") context: Context,
    @Query("player") player: UUID
  ): Call<List<BlocklistEntry>>

  @POST("/friendships/blocklist")
  fun addToBlocklist(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("target") target: UUID,
    @Query("activeUntil") activeUntil: Long
  ): Call<BlocklistEntry>

  @DELETE("/friendships/blocklist")
  fun removeFromBlocklist(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("target") target: UUID
  ): Call<Unit>
}