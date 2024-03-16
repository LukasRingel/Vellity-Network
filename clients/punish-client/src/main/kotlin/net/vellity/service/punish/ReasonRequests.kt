package net.vellity.service.punish

import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.service.punish.reason.PunishmentTimeUnit
import net.vellity.service.punish.reason.Reason
import net.vellity.service.punish.reason.ReasonDuration
import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ReasonRequests {
  @GET("/reasons")
  fun getReasons(
    @Query("context") context: Context
  ): Call<List<Reason>>

  @GET("/reasons/id")
  fun getReasonById(
    @Query("id") id: Int
  ): Call<Reason>

  @POST("/reasons")
  fun createReason(
    @Query("context") context: Context,
    @Query("name") name: String,
    @Query("type") type: PunishmentType
  ): Call<Reason>

  @DELETE("/reasons")
  fun deleteReason(
    @Query("id") id: Int
  ): Call<Unit>

  @POST("/reasons/duration")
  fun createOrUpdateDuration(
    @Query("reasonId") reasonId: Int,
    @Query("amount") amount: Int,
    @Query("timeunit") timeunit: PunishmentTimeUnit,
    @Query("multiplier") multiplier: Double
  ): Call<ReasonDuration>
}