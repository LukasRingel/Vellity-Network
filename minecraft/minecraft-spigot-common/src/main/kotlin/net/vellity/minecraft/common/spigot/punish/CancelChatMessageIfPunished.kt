package net.vellity.minecraft.common.spigot.punish

import io.papermc.paper.event.player.AsyncChatEvent
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.punishClient
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.translations.transformer.date.FormattedDateTime
import net.vellity.service.punish.punishments.Punishment
import net.vellity.service.punish.punishments.PunishmentRequestDetails
import net.vellity.service.punish.punishments.PunishmentType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.time.Clock
import java.util.*

class CancelChatMessageIfPunished : Listener {
  @EventHandler
  private fun cancelChatMessageIfPlayerHasActiveChatBan(event: AsyncChatEvent) {
    val activeBans = activeChatBans(event.player.uniqueId)
    if (activeBans.isEmpty()) {
      return
    }

    event.isCancelled = true

    val punishment = activeBans.first { it.reason != null }
    event.player.sendTranslatedMessage(
      "commons-punish-event-chat-banned",
      "reason",
      punishment.reason!!,
      "expires",
      FormattedDateTime.auto(punishment.activeUntil)
    )
  }

  private fun activeChatBans(player: UUID): List<Punishment> {
    val response = punishClient.punishments()
      .getActivePunishmentsOfType(context, player, PunishmentType.BAN_CHAT, PunishmentRequestDetails.WITHOUT)
      .execute()
    if (!response.isSuccessful) {
      return emptyList()
    }
    return response.body()!!.filter { punishment -> punishment.activeUntil > Clock.systemUTC().instant() }
  }
}