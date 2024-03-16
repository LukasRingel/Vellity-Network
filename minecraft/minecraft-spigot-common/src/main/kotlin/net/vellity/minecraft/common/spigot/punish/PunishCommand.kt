package net.vellity.minecraft.common.spigot.punish

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.permissions.NetworkPermissions
import net.vellity.minecraft.common.punishClient
import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.usercacheClient
import net.vellity.minecraft.common.validator.Validators
import net.vellity.service.punish.punishments.PunishmentActionType
import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.service.punish.reason.Reason
import org.bukkit.entity.Player
import java.util.UUID

class PunishCommand : PlayerCommand(
  name = "punish",
  description = "Punish a player for breaking the rules.",
  permission = NetworkPermissions.COMMAND_PUNISHMENT_PUNISH
) {
  override fun executeCommand(player: Player, args: Array<out String>) {
    if (args.size < 2) {
      player.sendTranslatedMessage("commons-punish-command-punish-usage")
      sendReasonInformation(player)
      return
    }

    val playerName = args[0]
    if (player.name.equals(playerName, true)) {
      player.sendTranslatedMessage("commons-punish-command-punish-validate-self")
      return
    }

    if (!Validators.isValidMinecraftName(playerName)) {
      player.sendTranslatedMessage("commons-punish-command-punish-validate-name")
      return
    }

    val targetUuid = tryFindUuid(player, playerName)
    if (targetUuid == null) {
      player.sendTranslatedMessage("commons-punish-command-punish-validate-unknown")
      return
    }

    val reason = findReasonWithName(args[1])
    if (reason == null) {
      player.sendTranslatedMessage("commons-punish-command-punish-validate-reason")
      sendReasonInformation(player)
      return
    }

    val hasActivePunishmentResponse = punishClient.punishments()
      .getActivePunishmentsOfType(context, targetUuid, reason.type)
      .execute()

    if (!hasActivePunishmentResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-punish-command-punish-error-http")
      return
    }

    if (hasActivePunishmentResponse.body()!!.isNotEmpty()) {
      player.sendTranslatedMessage("commons-punish-command-punish-validate-active", "player", playerName)
      return
    }

    player.sendTranslatedMessage("commons-punish-command-punish-creating")

    val punishmentResponse = punishClient.punishments()
      .createPunishment(context, reason.type, targetUuid, reason.id, player.uniqueId)
      .execute()

    if (!punishmentResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-punish-command-punish-error-http")
      return
    }

    player.sendTranslatedMessage("commons-punish-command-punish-created")

  }

  private fun findReasonWithName(reasonName: String): Reason? {
    return findReasons().firstOrNull { it.name.equals(reasonName, true) }
  }

  private fun tryFindUuid(player: Player, targetName: String): UUID? {
    val userCacheUuidResponse = usercacheClient.names().getUuidByName(targetName).execute()
    if (!userCacheUuidResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-punish-command-punish-validate-unknown")
      return null
    }
    return userCacheUuidResponse.body()!!
  }

  private fun sendReasonInformation(player: Player) {
    val reasons = findReasons()
    player.sendTranslatedMessage(
      "commons-punish-command-punish-usage-reasons-chat",
      "reasons",
      reasons.filter { it.type == PunishmentType.BAN_CHAT }.joinToString(", ") { it.name }
    )
    player.sendTranslatedMessage(
      "commons-punish-command-punish-usage-reasons-network",
      "reasons",
      reasons.filter { it.type == PunishmentType.BAN_NETWORK }.joinToString(", ") { it.name }
    )
  }

  private fun findReasons(): List<Reason> {
    val response = punishClient.reasons().getReasons(context).execute()
    if (!response.isSuccessful) {
      return emptyList()
    }
    return response.body()!!
  }
}