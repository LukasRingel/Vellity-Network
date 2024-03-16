package net.vellity.service.punish.punishments

import net.vellity.utils.context.Context
import java.util.*

interface PunishmentService {
  fun getActivePunishmentsWithDetails(context: Context, player: UUID): List<Punishment>

  fun getActivePunishmentsWithoutDetails(context: Context, player: UUID): List<Punishment>

  fun getActivePunishmentsWithDetailsOfType(context: Context, player: UUID, type: PunishmentType): List<Punishment>

  fun getActivePunishmentsWithoutDetailsOfType(context: Context, player: UUID, type: PunishmentType): List<Punishment>

  fun getPunishmentWithId(id: Int): Punishment

  fun createPunishment(context: Context, type: PunishmentType, player: UUID, reason: Int, actor: UUID): Punishment

  fun deletePunishment(id: Int, deletedBy: UUID, reason: String)
}