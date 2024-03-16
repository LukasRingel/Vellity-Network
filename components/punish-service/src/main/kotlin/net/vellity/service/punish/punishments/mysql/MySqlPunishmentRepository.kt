package net.vellity.service.punish.punishments.mysql

import net.vellity.service.punish.punishments.Punishment
import net.vellity.service.punish.punishments.PunishmentActionType
import net.vellity.service.punish.punishments.PunishmentRepository
import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.service.punish.punishments.dto.StoredPunishment
import net.vellity.service.punish.punishments.mysql.actions.InsertActionForPunishment
import net.vellity.service.punish.punishments.mysql.actions.SelectActionsForPunishments
import net.vellity.service.punish.punishments.mysql.selectActive.SelectActivePunishmentsWithTypeFilter
import net.vellity.service.punish.punishments.mysql.selectActive.SelectActivePunishmentsWithoutTypeFilter
import net.vellity.service.punish.punishments.mysql.selectActive.details.SelectProofsForPunishments
import net.vellity.service.punish.punishments.mysql.update.DeletePunishment
import net.vellity.service.punish.punishments.mysql.update.InsertPunishment
import net.vellity.service.punish.reason.Reason
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class MySqlPunishmentRepository(private val mySqlConnection: MySqlConnection) : PunishmentRepository {
  override fun getStoredPunishmentWithId(id: Int): StoredPunishment? {
    return mySqlConnection.executeQuery(SelectPunishmentWithId(id)).firstOrNull()
  }

  override fun getActivePunishmentsWithDetails(context: Context, player: UUID): List<StoredPunishment> {
    val punishments = getActivePunishmentsWithoutDetails(context, player)
    if (punishments.isEmpty()) {
      return emptyList()
    }
    loadDetailsForPunishments(punishments)
    return punishments
  }

  override fun getActivePunishmentsWithoutDetails(context: Context, player: UUID): List<StoredPunishment> {
    return mySqlConnection.executeQuery(SelectActivePunishmentsWithoutTypeFilter(context, player))
  }

  override fun getActivePunishmentsWithDetailsOfType(
    context: Context,
    player: UUID,
    type: PunishmentType
  ): List<StoredPunishment> {
    val punishments = getActivePunishmentsWithoutDetailsOfType(context, player, type)
    if (punishments.isEmpty()) {
      return emptyList()
    }
    loadDetailsForPunishments(punishments)
    return punishments
  }

  override fun getActivePunishmentsWithoutDetailsOfType(
    context: Context,
    player: UUID,
    type: PunishmentType
  ): List<StoredPunishment> {
    return mySqlConnection.executeQuery(SelectActivePunishmentsWithTypeFilter(context, player, type))
  }

  override fun createPunishment(
    context: Context,
    type: PunishmentType,
    player: UUID,
    reason: Reason,
    activeUntil: Instant
  ): Punishment {
    val id =
      mySqlConnection.executeUpdateAndGetId(InsertPunishment(context, type, player, reason, activeUntil))
    return Punishment(id, type, context, player, reason, emptyList(), emptyList(), activeUntil, Instant.now())
  }

  override fun deletePunishment(id: Int) {
    mySqlConnection.executeUpdate(DeletePunishment(id))
  }

  override fun logAction(
    punishment: Int,
    action: PunishmentActionType,
    actor: UUID,
    beforeValue: String,
    afterValue: String
  ) {
    mySqlConnection.executeUpdate(InsertActionForPunishment(punishment, action, actor, beforeValue, afterValue))
  }

  private fun loadDetailsForPunishments(punishments: List<StoredPunishment>) {
    val actionsOfAllPunishments = mySqlConnection.executeQuery(SelectActionsForPunishments(punishments.map { it.id }))
    actionsOfAllPunishments.groupBy { it.punishmentId }.forEach { (punishmentId, actions) ->
      punishments.find { it.id == punishmentId }?.actions = actions
    }

    val proofsForPunishments = mySqlConnection.executeQuery(SelectProofsForPunishments(punishments.map { it.id }))
    proofsForPunishments.groupBy { it.punishmentId }.forEach { (punishmentId, proofs) ->
      punishments.find { it.id == punishmentId }?.proofs = proofs
    }
  }
}