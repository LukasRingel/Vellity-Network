package net.vellity.minecraft.common.spigot.extensions

import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.Title.Times
import net.vellity.minecraft.common.babbelClient
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.spigot.item.BlueprintToSpigot
import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.translations.format.MiniMessageFormat
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object PlayerBabbelExtensions {
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
    BlueprintToSpigot.localeForPlayer = { player ->
      player.language()
    }
  }

  fun Player.language(useCache: Boolean = true): Locale {
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

  fun Player.updateLocalLanguageCache(locale: Locale) {
    localeCache.remove(this.uniqueId)
    localeCache.put(this.uniqueId, locale)
  }

  fun Player.translate(key: String): Component {
    return MiniMessageFormat.format(this.translation(key))
  }

  fun Player.translate(key: String, vararg args: Any): Component {
    return MiniMessageFormat.format(this.translation(key, *args))
  }

  fun Player.translate(key: String, map: Map<String, Any>): Component {
    return MiniMessageFormat.format(this.translation(key, map))
  }

  fun Player.translation(key: String): String {
    val locale = this.language()
    return TranslationRepository.get(key, locale)
  }

  fun Player.translation(key: String, vararg args: Any): String {
    val locale = this.language()
    return TranslationRepository.get(key, locale, arrayOf(*args))
  }

  fun Player.translation(key: String, map: Map<String, Any>): String {
    val locale = this.language()
    return TranslationRepository.get(key, locale, map)
  }

  fun Player.sendTranslatedMessage(key: String, vararg args: Any) {
    this.sendMessage(this.translate(key, *args))
  }

  fun Player.sendTranslatedMessage(key: String, map: Map<String, Any>) {
    this.sendMessage(this.translate(key, map))
  }

  fun Player.sendTranslatedTitle(big: String, small: String) {
    this.showTitle(Title.title(this.translate(big), this.translate(small)))
  }

  fun Player.sendTranslatedTitle(big: String, small: String, times: Times) {
    this.showTitle(Title.title(this.translate(big), this.translate(small), times))
  }
}