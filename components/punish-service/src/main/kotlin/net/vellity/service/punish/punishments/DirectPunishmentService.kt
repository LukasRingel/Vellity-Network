package net.vellity.service.punish.punishments

import net.vellity.service.punish.punishments.discord.PunishmentCreateDiscordAudit
import net.vellity.service.punish.punishments.discord.PunishmentDeleteDiscordAudit
import net.vellity.service.punish.punishments.dto.StoredPunishment
import net.vellity.service.punish.reason.ReasonsService
import net.vellity.utils.audit.pool.submitAuditChange
import net.vellity.utils.configuration.lifetimeDate
import net.vellity.utils.context.Context
import org.springframework.stereotype.Service
import java.util.*

@Service
class DirectPunishmentService(
  private val punishmentRepository: PunishmentRepository,
  private val reasonsService: ReasonsService
) : PunishmentService {
  override fun getActivePunishmentsWithDetails(context: Context, player: UUID): List<Punishment> {
    return StoredPunishment.mapStoredPunishmentsToPunishmentWithReason(
      punishmentRepository.getActivePunishmentsWithDetails(
        context,
        player
      ),
      reasonsService
    )
  }

  override fun getActivePunishmentsWithoutDetails(context: Context, player: UUID): List<Punishment> {
    return StoredPunishment.mapStoredPunishmentsToPunishmentWithReason(
      punishmentRepository.getActivePunishmentsWithoutDetails(
        context,
        player
      ),
      reasonsService
    )
  }

  override fun getActivePunishmentsWithDetailsOfType(
    context: Context,
    player: UUID,
    type: PunishmentType
  ): List<Punishment> {
    return StoredPunishment.mapStoredPunishmentsToPunishmentWithReason(
      punishmentRepository.getActivePunishmentsWithDetailsOfType(
        context,
        player,
        type
      ),
      reasonsService
    )
  }

  override fun getActivePunishmentsWithoutDetailsOfType(
    context: Context,
    player: UUID,
    type: PunishmentType
  ): List<Punishment> {
    return StoredPunishment.mapStoredPunishmentsToPunishmentWithReason(
      punishmentRepository.getActivePunishmentsWithoutDetailsOfType(
        context,
        player,
        type
      ),
      reasonsService
    )
  }

  override fun getPunishmentWithId(id: Int): Punishment {
    val storedPunishment = punishmentRepository.getStoredPunishmentWithId(id)
      ?: throw IllegalArgumentException("Punishment with id $id does not exist")
    return StoredPunishment.mapStoredPunishmentToPunishmentWithReason(
      storedPunishment,
      reasonsService
    )
  }

  override fun createPunishment(
    context: Context,
    type: PunishmentType,
    player: UUID,
    reason: Int,
    actor: UUID
  ): Punishment {
    val reasonById = reasonsService.getReasonById(reason)
      ?: throw IllegalArgumentException("Reason with id $reason does not exist")

    val punishment = punishmentRepository.createPunishment(
      context,
      type,
      player,
      reasonById,
      lifetimeDate
    )

    submitAuditChange {
      punishmentRepository.logAction(
        punishment.id,
        PunishmentActionType.CREATE,
        actor,
        "",
        ""
      )
    }

    PunishmentCreateDiscordAudit.broadcast(punishment, actor)

    return punishment
  }

  override fun deletePunishment(id: Int, deletedBy: UUID, reason: String) {
    val punishment = punishmentRepository.getStoredPunishmentWithId(id)
      ?: throw IllegalArgumentException("Punishment with id $id does not exist")
    val punishmentReason = reasonsService.getReasonById(punishment.reason)
      ?: throw IllegalArgumentException("Reason with id ${punishment.reason} does not exist")

    submitAuditChange {
      punishmentRepository.logAction(
        id,
        PunishmentActionType.PARDON,
        deletedBy,
        "",
        reason
      )
    }

    PunishmentDeleteDiscordAudit.broadcast(punishment, punishmentReason, deletedBy, reason)
    punishmentRepository.deletePunishment(id)
  }
}