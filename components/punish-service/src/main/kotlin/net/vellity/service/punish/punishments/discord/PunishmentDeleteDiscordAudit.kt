package net.vellity.service.punish.punishments.discord

import club.minnced.discord.webhook.WebhookClient
import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import net.vellity.service.punish.punishments.dto.StoredPunishment
import net.vellity.service.punish.reason.Reason
import net.vellity.utils.alert.discord.extensions.field
import net.vellity.utils.audit.pool.submitAuditChange
import net.vellity.utils.configuration.Environment
import net.vellity.utils.mojang.MojangServers
import java.util.*

class PunishmentDeleteDiscordAudit {
  companion object {
    fun broadcast(punishment: StoredPunishment, punishmentReason: Reason, deletedBy: UUID, reason: String) {
      submitAuditChange {
        val client = WebhookClient.withUrl(
          Environment.getOrDefault(
            "PUNISH_AUDIT_DISCORD_WEBHOOK_URL_" + punishment.context.name.uppercase(),
            ""
          )
        )
        val webhookEmbed = WebhookEmbedBuilder()
          .setTitle(
            WebhookEmbed.EmbedTitle(
              "Punishment Deleted",
              null
            )
          )
          .setThumbnailUrl(punishment.context.imageUrl)
          .setDescription("A punishment was deleted")
          .addField(field("Player Name", MojangServers.nameFromUuid(punishment.player)))
          .addField(field("Player UUID", punishment.player.toString()))
          .addField(field("Type", punishment.type.name))
          .addField(field("Deleted by Name", MojangServers.nameFromUuid(deletedBy)))
          .addField(field("Deleted by UUID", deletedBy.toString()))
          .addField(field("Original Reason", punishmentReason.name))
          .addField(field("Original Until", punishment.activeUntil.toString()))
          .addField(field("Creation Date", punishment.createdAt.toString()))
          .addField(field("Deletion Reason", reason))
          .build()
        client.send(webhookEmbed).get()
      }
    }
  }
}