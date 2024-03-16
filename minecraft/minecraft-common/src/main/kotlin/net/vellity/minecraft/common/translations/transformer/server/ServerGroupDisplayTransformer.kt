package net.vellity.minecraft.common.translations.transformer.server

import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import java.util.*

class ServerGroupDisplayTransformer : TypeTransformer<ServerGroupDisplay> {
  override fun toString(instance: Any, locale: Locale): String =
    TranslationRepository.get(
      "commons-servergroup-" + (instance as ServerGroupDisplay).getLocalServerGroupDisplay(),
      locale
    )

  override fun fallback(locale: Locale): String =
    "??"

  override fun supports(clazz: Class<*>): Boolean =
    ServerGroupDisplay::class.java.isAssignableFrom(clazz)
}