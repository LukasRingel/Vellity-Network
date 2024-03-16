package net.vellity.minecraft.common.translations.transformer.punish

import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import net.vellity.service.punish.reason.Reason
import java.util.*

class PunishmentReasonTypeTransformer : TypeTransformer<Reason> {
  override fun toString(instance: Any, locale: Locale): String =
    TranslationRepository.get("commons-punish-reason-" + (instance as Reason).name, locale)

  override fun fallback(locale: Locale): String =
    "Unknown reason"

  override fun supports(clazz: Class<*>): Boolean =
    Reason::class.java.isAssignableFrom(clazz)
}