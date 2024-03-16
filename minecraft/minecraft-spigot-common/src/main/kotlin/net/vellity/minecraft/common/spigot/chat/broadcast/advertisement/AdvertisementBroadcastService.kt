package net.vellity.minecraft.common.spigot.chat.broadcast.advertisement

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.language
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.usercacheClient
import net.vellity.service.usercache.notification.Notification
import net.vellity.utils.configuration.Environment
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object AdvertisementBroadcastService {
  fun runIfActivated() {
    if (isFeatureDisabled()) {
      return
    }

    Executors.newSingleThreadScheduledExecutor().schedule({
      for (onlinePlayer in Bukkit.getOnlinePlayers()) {
        handlePlayer(onlinePlayer)
      }
    }, 5, TimeUnit.MINUTES)
  }

  private fun handlePlayer(player: Player) {
    val response = usercacheClient.notifications().getAllNotifications(context, player.uniqueId).execute()

    if (!response.isSuccessful) {
      return
    }

    val notifications = response.body() ?: return
    val possibleTexts = mutableListOf<String>()

    for (type in Type.values()) {
      val toggledNotificationOfSameType = notifications.find { notification ->
        notification.notification == type.notification
      } ?: continue

      if (!toggledNotificationOfSameType.enabled) {
        continue
      }

      TranslationRepository.getTranslationsStartingWith(player.language(), type.translationPrefix)
        .forEach { (_, value) ->
          possibleTexts.add(value)
        }
    }

    if (possibleTexts.isEmpty()) {
      return
    }

    player.sendTranslatedMessage(possibleTexts.random())
  }

  private fun isFeatureDisabled() =
    !Environment.getAsBooleanOrDefault("SERVER_BROADCAST_TIPPS_AND_ADVERTISEMENT", true)

  private enum class Type(val notification: Notification, val translationPrefix: String) {
    TIPPS(Notification.GLOBAL_BROADCAST_TIPPS, "commons-broadcast-tipps-"),
    ADVERTISEMENT(Notification.GLOBAL_BROADCAST_ADVERTISEMENT, "commons-broadcast-advertisement-");
  }
}