package net.vellity.minecraft.common.translations.transformer.player

import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import net.vellity.service.usercache.notification.Notification
import java.util.*

class NotificationTypeTransformer: TypeTransformer<Notification> {
  override fun toString(instance: Any, locale: Locale): String =
    TranslationRepository.get("commons-notification-" + (instance as Notification).internalName, locale)

  override fun fallback(locale: Locale): String =
    "Unknown notification"

  override fun supports(clazz: Class<*>): Boolean =
    Notification::class.java.isAssignableFrom(clazz)
}