package net.vellity.service.punish.punishments.discord

import club.minnced.discord.webhook.WebhookClient
import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import net.vellity.service.punish.punishments.Punishment
import net.vellity.utils.alert.discord.extensions.field
import net.vellity.utils.audit.pool.submitAuditChange
import net.vellity.utils.configuration.Environment
import net.vellity.utils.mojang.MojangServers
import java.util.UUID

class PunishmentCreateDiscordAudit {
  companion object {
    fun broadcast(punishment: Punishment, creator: UUID) {
      submitAuditChange {
        val client = WebhookClient.withUrl(Environment.getOrDefault(
          "PUNISH_AUDIT_DISCORD_WEBHOOK_URL_" + punishment.context.name.uppercase(),
          ""
        ))
        val webhookEmbed = WebhookEmbedBuilder()
          .setTitle(
            WebhookEmbed.EmbedTitle(
              "Punishment Created",
              null
            )
          )
          .setThumbnailUrl(punishment.context.imageUrl)
          .setDescription("A new punishment was created")
          .addField(field("Player Name", MojangServers.nameFromUuid(punishment.player)))
          .addField(field("Player UUID", punishment.player.toString()))
          .addField(field("Type", punishment.type.name))
          .addField(field("Creator Name", MojangServers.nameFromUuid(creator)))
          .addField(field("Creator UUID", creator.toString()))
          .addField(field("Reason", punishment.reason!!.name))
          .addField(field("Until", punishment.activeUntil.toString()))
          .build()
        client.send(webhookEmbed).get()
      }
    }
  }
}