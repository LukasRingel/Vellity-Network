package net.vellity.minecraft.velocity.extensions

import com.velocitypowered.api.proxy.Player
import net.kyori.adventure.text.Component
import net.vellity.minecraft.common.babbelClient
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.translations.format.MiniMessageFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class BabbelPlayerExtensions {
  companion object {
    private val localeCache = mutableMapOf<UUID, Locale>()

    init {
      Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
        {
          localeCache.clear()
        },
        0,
        1,
        TimeUnit.MINUTES
      )
    }

    fun Player.getLocale(useCache: Boolean = true): Locale {
      if (localeCache.containsKey(this.uniqueId) && useCache) {
        return localeCache[this.uniqueId]!!
      }

      val response = babbelClient.users().getUser(context, this.uniqueId).execute()
      if (!response.isSuccessful || response.body() == null) {
        return Locale.US
      }

      val locale = response.body()!!
      if (useCache) {
        localeCache[this.uniqueId] = locale
      }
      return locale
    }

    fun Player.translate(key: String): Component {
      return MiniMessageFormat.format(this.translation(key))
    }

    fun Player.translate(key: String, vararg args: Any): Component {
      return MiniMessageFormat.format(this.translation(key, args))
    }

    fun Player.translation(key: String): String {
      val locale = this.getLocale()
      return TranslationRepository.get(key, locale)
    }

    fun Player.translation(key: String, vararg args: Any): String {
      val locale = this.getLocale()
      return TranslationRepository.get(key, locale, arrayOf(args))
    }
  }
}