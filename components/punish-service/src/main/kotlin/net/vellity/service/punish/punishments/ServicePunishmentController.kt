package net.vellity.service.punish.punishments

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ServicePunishmentController(private val punishmentService: PunishmentService) : PunishmentController {
  override fun getActivePunishments(
    context: Context,
    player: UUID,
    details: PunishmentRequestDetails
  ): ResponseEntity<List<Punishment>> {
    return ResponseEntity.ok(
      if (details == PunishmentRequestDetails.WITHOUT) {
        punishmentService.getActivePunishmentsWithoutDetails(context, player)
      } else {
        punishmentService.getActivePunishmentsWithDetails(context, player)
      }
    )
  }

  override fun getPunishmentWithId(id: Int): ResponseEntity<Punishment> {
    return ResponseEntity.ok(punishmentService.getPunishmentWithId(id))
  }


  override fun getActivePunishmentsOfType(
    context: Context,
    player: UUID,
    type: PunishmentType,
    details: PunishmentRequestDetails
  ): ResponseEntity<List<Punishment>> {
    return ResponseEntity.ok(
      if (details == PunishmentRequestDetails.WITHOUT) {
        punishmentService.getActivePunishmentsWithoutDetailsOfType(context, player, type)
      } else {
        punishmentService.getActivePunishmentsWithDetailsOfType(context, player, type)
      }
    )
  }

  override fun createPunishment(
    context: Context,
    type: PunishmentType,
    player: UUID,
    reason: Int,
    actor: UUID
  ): ResponseEntity<Punishment> {
    return ResponseEntity.ok(punishmentService.createPunishment(context, type, player, reason, actor))
  }

  override fun deletePunishment(id: Int, deletedBy: UUID, reason: String): ResponseEntity<Unit> {
    return ResponseEntity.ok(punishmentService.deletePunishment(id, deletedBy, reason))
  }
}