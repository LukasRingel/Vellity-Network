package net.vellity.service.access.group.log

import club.minnced.discord.webhook.WebhookClient
import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import net.vellity.service.access.group.PermissionGroupRepository
import net.vellity.service.access.group.log.model.GroupMetaDataChange
import net.vellity.service.access.group.log.model.GroupPermissionChange
import net.vellity.utils.alert.discord.extensions.field
import net.vellity.utils.audit.pool.submitAuditChange
import net.vellity.utils.configuration.Environment
import org.springframework.stereotype.Service

@Service
class DirectGroupChangeLogService(
  private val changeRepository: GroupChangeRepository,
  private val groupRepository: PermissionGroupRepository
) : GroupChangeLogService {
  override fun logMetaDataChange(change: GroupMetaDataChange) {
    submitAuditChange {
      changeRepository.logMetaDataChange(change)
      logMetaDataChangeToDiscord(change)
    }
  }

  override fun logPermissionChange(change: GroupPermissionChange) {
    submitAuditChange {
      changeRepository.logPermissionChange(change)
      logPermissionChangeToDiscord(change)
    }
  }

  private fun logPermissionChangeToDiscord(change: GroupPermissionChange) {
    val permissionGroup = groupRepository.getPermissionGroup(change.group)!!

    val client = WebhookClient.withUrl(DISCORD_WEBHOOK_URL)
    val webhookEmbed = WebhookEmbedBuilder()
      .setTitle(
        WebhookEmbed.EmbedTitle(
          "Group Permission Change",
          null
        )
      )
      .setThumbnailUrl(permissionGroup.context.imageUrl)
      .setDescription("A permission of a group has changed")
      .addField(field("Group Name", permissionGroup.name))
      .addField(field("Group Context", permissionGroup.context.name))
      .addField(field("Group ID", permissionGroup.id))
      .addField(field("Permission", change.permission))
      .addField(field("Action", if (change.newValue) "Added" else "Removed"))
      .build()
    client.send(webhookEmbed).get()
  }

  private fun logMetaDataChangeToDiscord(change: GroupMetaDataChange) {
    val permissionGroup = groupRepository.getPermissionGroup(change.group)!!

    val client = WebhookClient.withUrl(DISCORD_WEBHOOK_URL)
    val webhookEmbed = WebhookEmbedBuilder()
      .setTitle(
        WebhookEmbed.EmbedTitle(
          "Group Meta Data Change",
          null
        )
      )
      .setThumbnailUrl(permissionGroup.context.imageUrl)
      .setDescription("A meta data of a group has changed")
      .addField(field("Group Name", permissionGroup.name))
      .addField(field("Group Context", permissionGroup.context.name))
      .addField(field("Group ID", permissionGroup.id))
      .addField(field("Meta Data Key", change.metaData))
      .addField(field("Old Value", change.oldValue))
      .addField(field("New Value", change.newValue))
      .build()
    client.send(webhookEmbed).get()
  }

  companion object {
    private val DISCORD_WEBHOOK_URL = Environment.getOrDefault("AUDIT_DISCORD_WEBHOOK_URL", "")
  }
}