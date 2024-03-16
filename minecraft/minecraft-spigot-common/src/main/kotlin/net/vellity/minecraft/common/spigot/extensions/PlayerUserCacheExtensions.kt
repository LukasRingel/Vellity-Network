package net.vellity.minecraft.common.spigot.extensions

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.spigot.extensions.PlayerAccessExtensions.hasAccess
import net.vellity.minecraft.common.usercacheClient
import net.vellity.service.usercache.notification.Notification
import net.vellity.service.usercache.session.UserSession
import org.bukkit.entity.Player

object PlayerUserCacheExtensions {
  fun Player.session(): UserSession {
    val response = usercacheClient.sessions().getCurrentSession(context, this.uniqueId).execute()
    if (!response.isSuccessful) {
      return UserSession.empty(this.uniqueId, 0)
    }
    return response.body()!!
  }

  fun Player.hasNotificationEnabled(notification: Notification): Boolean {
    if (notification.permission.isNotEmpty() && !this.hasAccess(notification.permission)) {
      return false
    }

    val response = usercacheClient.notifications().getToggledNotification(
      context,
      this.uniqueId,
      notification
    ).execute()

    if (!response.isSuccessful) {
      return notification.defaultEnabled
    }

    return response.body()!!.enabled
  }
}