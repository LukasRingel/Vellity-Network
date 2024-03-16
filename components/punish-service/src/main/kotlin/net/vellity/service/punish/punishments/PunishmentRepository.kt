package net.vellity.service.punish.punishments

import net.vellity.service.punish.punishments.dto.StoredPunishment
import net.vellity.service.punish.reason.Reason
import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

interface PunishmentRepository {
  fun getStoredPunishmentWithId(id: Int): StoredPunishment?

  fun getActivePunishmentsWithDetails(context: Context, player: UUID): List<StoredPunishment>

  fun getActivePunishmentsWithoutDetails(context: Context, player: UUID): List<StoredPunishment>

  fun getActivePunishmentsWithDetailsOfType(context: Context, player: UUID, type: PunishmentType): List<StoredPunishment>

  fun getActivePunishmentsWithoutDetailsOfType(context: Context, player: UUID, type: PunishmentType): List<StoredPunishment>

  fun createPunishment(context: Context, type: PunishmentType, player: UUID, reason: Reason, activeUntil: Instant): Punishment

  fun deletePunishment(id: Int)

  fun logAction(punishment: Int, action: PunishmentActionType, actor: UUID, beforeValue: String, afterValue: String)
}