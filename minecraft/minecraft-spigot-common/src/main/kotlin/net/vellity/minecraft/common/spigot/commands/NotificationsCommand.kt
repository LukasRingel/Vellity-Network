package net.vellity.minecraft.common.spigot.commands

import net.kyori.adventure.text.event.ClickEvent
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerAccessExtensions.hasAccess
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.translations.transformer.data.ClickableSeperatedIterable
import net.vellity.minecraft.common.usercacheClient
import net.vellity.service.usercache.notification.Notification
import net.vellity.service.usercache.notification.ToggledNotification
import org.bukkit.entity.Player

class NotificationsCommand : PlayerCommand(
  name = "notifications",
  description = "Toggle notifications",
  aliases = mutableListOf("notify")
) {
  override fun executeCommand(player: Player, args: Array<out String>) {
    try {
      val response = usercacheClient.notifications().getAllNotifications(context, player.uniqueId).execute()

      if (!response.isSuccessful) {
        player.sendTranslatedMessage("commons-command-notifications-error-http")
        return
      }

      val notifications = response.body()!!.filter { player.hasAccess(it.notification.permission) }

      if (notifications.isEmpty()) {
        sendAccessErrorMessage(player)
        return
      }

      if (args.isEmpty()) {
        sendNotificationOverview(player, notifications)
        return
      }

      val targetNotification = Notification.byInternalName(args[0])

      if (targetNotification == null) {
        player.sendTranslatedMessage(
          "commons-command-notifications-error-unknown",
          "notification",
          args[0]
        )
        return
      }

      val newEnabledStatus = notifications.first { it.notification == targetNotification }.enabled.not()
      val updateRequest = usercacheClient.notifications()
        .updateNotification(context, player.uniqueId, targetNotification, newEnabledStatus)
        .execute()

      if (!updateRequest.isSuccessful) {
        player.sendTranslatedMessage("commons-command-notifications-error-http")
        return
      }

      if (args.size == 2 && args[1] == "overview") {
        notifications.find { it.notification == targetNotification }!!.enabled = newEnabledStatus
        sendNotificationOverview(player, notifications)
        return
      }

      player.sendTranslatedMessage(
        "commons-command-notifications-update" + if (newEnabledStatus) "-enabled" else "-disabled",
        "notification",
        targetNotification.internalName,
        "enabled",
        newEnabledStatus
      )
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  private fun sendNotificationOverview(
    player: Player,
    notifications: List<ToggledNotification>
  ) {
    player.sendTranslatedMessage(
      "commons-command-notifications-overview",
      "notifications",
      ClickableSeperatedIterable.of(notifications.map { it.notification }, { notification ->
        ClickEvent.runCommand("/notifications ${(notification as Notification).internalName} overview")
      }, { current ->
        val notification = current as Notification
        val find = notifications.find { it.notification == notification }!!
        if (find.enabled) "enabled" else "disabled"
      })
    )
  }
}