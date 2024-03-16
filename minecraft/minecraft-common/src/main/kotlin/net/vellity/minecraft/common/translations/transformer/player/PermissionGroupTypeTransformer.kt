package net.vellity.minecraft.common.translations.transformer.player

import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import net.vellity.service.access.group.PermissionGroup
import net.vellity.service.usercache.notification.Notification
import java.util.*

class PermissionGroupTypeTransformer: TypeTransformer<PermissionGroup> {
  override fun toString(instance: Any, locale: Locale): String =
    TranslationRepository.get("commons-permissiongroup-" + (instance as PermissionGroup).name, locale)

  override fun fallback(locale: Locale): String =
    "Unknown group"

  override fun supports(clazz: Class<*>): Boolean =
    PermissionGroup::class.java.isAssignableFrom(clazz)
}