package net.vellity.service.access.user.log

import club.minnced.discord.webhook.WebhookClient
import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import net.vellity.service.access.group.PermissionGroupRepository
import net.vellity.service.access.user.log.model.UserGroupChange
import net.vellity.service.access.user.log.model.UserPermissionChange
import net.vellity.utils.alert.discord.extensions.field
import net.vellity.utils.audit.pool.submitAuditChange
import net.vellity.utils.configuration.Environment
import net.vellity.utils.mojang.MojangServers
import org.springframework.stereotype.Service

@Service
class DirectUserChangeLogService(
  private val changeRepository: UserChangeRepository,
  private val groupRepository: PermissionGroupRepository
) : UserChangeLogService {
  override fun logGroupChange(change: UserGroupChange) {
    submitAuditChange {
      changeRepository.logGroupChange(change)
      logGroupChangeToDiscord(change)
    }
  }

  override fun logPermissionChange(change: UserPermissionChange) {
    submitAuditChange {
      changeRepository.logPermissionChange(change)
      logPermissionChangeToDiscord(change)
    }
  }

  private fun logPermissionChangeToDiscord(change: UserPermissionChange) {
    val client = WebhookClient.withUrl(DISCORD_WEBHOOK_URL)
    val webhookEmbed = WebhookEmbedBuilder()
      .setTitle(
        WebhookEmbed.EmbedTitle(
          "User Permission Change",
          null
        )
      )
      .setThumbnailUrl(change.context.imageUrl)
      .setDescription("A permission of a user has changed")
      .addField(field("User Name", MojangServers.nameFromUuid(change.player)))
      .addField(field("User uuid", change.player.toString()))
      .addField(field("Permission", change.permission))
      .addField(field("ExpireAt", change.expireAt.toString()))
      .addField(field("Action", if (change.newValue) "Added" else "Removed"))
      .build()
    client.send(webhookEmbed).get()
  }

  private fun logGroupChangeToDiscord(change: UserGroupChange) {
    val permissionGroup = groupRepository.getPermissionGroup(change.group)!!

    val client = WebhookClient.withUrl(DISCORD_WEBHOOK_URL)
    val webhookEmbed = WebhookEmbedBuilder()
      .setTitle(
        WebhookEmbed.EmbedTitle(
          "User Group Change",
          null
        )
      )
      .setThumbnailUrl(permissionGroup.context.imageUrl)
      .setDescription("A meta data of a group has changed")
      .addField(field("Group Name", permissionGroup.name))
      .addField(field("Group Context", permissionGroup.context.name))
      .addField(field("Group ID", permissionGroup.id))
      .addField(field("User Name", MojangServers.nameFromUuid(change.player)))
      .addField(field("User uuid", change.player.toString()))
      .addField(field("ExpireAt", change.expireAt.toString()))
      .addField(field("Action", if (change.newValue) "Added" else "Removed"))
      .build()
    client.send(webhookEmbed).get()
  }

  companion object {
    private val DISCORD_WEBHOOK_URL = Environment.getOrDefault("AUDIT_DISCORD_WEBHOOK_URL", "")
  }
}