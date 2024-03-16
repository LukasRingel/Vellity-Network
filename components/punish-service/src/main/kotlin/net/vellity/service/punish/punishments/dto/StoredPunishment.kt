package net.vellity.service.punish.punishments.dto

import net.vellity.service.punish.punishments.Punishment
import net.vellity.service.punish.punishments.PunishmentAction
import net.vellity.service.punish.punishments.PunishmentProof
import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.service.punish.reason.ReasonsService
import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

data class StoredPunishment(
  val id: Int,
  val type: PunishmentType,
  val context: Context,
  val player: UUID,
  var reason: Int,
  var actions: List<PunishmentAction>,
  var proofs: List<PunishmentProof>,
  val activeUntil: Instant,
  val createdAt: Instant
) {
  companion object {
    fun mapStoredPunishmentsToPunishmentWithReason(
      stored: List<StoredPunishment>,
      reasonsService: ReasonsService
    ): List<Punishment> {
      val punishments = mutableListOf<Punishment>()
      for (storedPunishment in stored) {
        punishments.add(mapStoredPunishmentToPunishmentWithReason(storedPunishment, reasonsService))
      }
      return punishments
    }

    fun mapStoredPunishmentToPunishmentWithReason(
      stored: StoredPunishment,
      reasonsService: ReasonsService
    ): Punishment {
      return Punishment(
        id = stored.id,
        type = stored.type,
        context = stored.context,
        player = stored.player,
        reason = reasonsService.getReasonById(stored.reason),
        actions = stored.actions,
        proofs = stored.proofs,
        activeUntil = stored.activeUntil,
        createdAt = stored.createdAt
      )
    }
  }
}