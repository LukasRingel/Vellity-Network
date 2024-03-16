package net.vellity.service.punish

import net.vellity.service.punish.punishments.Punishment
import net.vellity.service.punish.punishments.PunishmentRequestDetails
import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

interface PunishmentRequests {
  @GET("/punishments")
  fun getActivePunishments(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("details") details: PunishmentRequestDetails = PunishmentRequestDetails.INCLUDE
  ): Call<List<Punishment>>

  @GET("/punishments/type")
  fun getActivePunishmentsOfType(
    @Query("context") context: Context,
    @Query("player") player: UUID,
    @Query("type") type: PunishmentType,
    @Query("details") details: PunishmentRequestDetails = PunishmentRequestDetails.INCLUDE
  ): Call<List<Punishment>>

  @POST("/punishments")
  fun createPunishment(
    @Query("context") context: Context,
    @Query("type") type: PunishmentType,
    @Query("player") player: UUID,
    @Query("reason") reason: Int,
    @Query("actor") actor: UUID
  ): Call<Punishment>

  @DELETE("/punishments")
  fun deletePunishment(
    @Query("id") id: Int,
    @Query("deletedBy") deletedBy: UUID,
    @Query("reason") reason: String
  ): Call<Unit>
}