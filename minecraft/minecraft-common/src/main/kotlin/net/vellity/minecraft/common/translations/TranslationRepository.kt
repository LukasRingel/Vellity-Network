package net.vellity.minecraft.common.translations

import net.kyori.adventure.text.format.TextColor
import net.vellity.minecraft.common.babbelClient
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.translations.format.MiniMessageFormat
import net.vellity.minecraft.common.translations.transformer.Transformation
import net.vellity.service.babbel.ColorConfiguration
import java.util.*
import java.util.concurrent.Executors

object TranslationRepository {
  private var colorConfig = ColorConfiguration(mapOf())
  private var translations: MutableMap<Locale, Map<String, String>> = mutableMapOf()

  fun startUpdateTask() {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
      updateRepository()
    }, 0, 30, java.util.concurrent.TimeUnit.SECONDS)
  }

  fun getColor(key: String): TextColor {
    return TextColor.fromCSSHexString(colorConfig.colors[key] ?: "#ffffff")!!
  }

  fun getColorString(key: String): String {
    return colorConfig.colors[key] ?: "#ffffff"
  }

  fun updateRepository() {
    val response = babbelClient.translations().bundle(context).execute()
    if (!response.isSuccessful) {
      throw RuntimeException("Failed to update translation repository")
    }
    colorConfig = response.body()!!.colorConfiguration
    MiniMessageFormat.clearResolvers()
    MiniMessageFormat.addResolvers(colorConfig)

    translations.clear()
    for (translation in response.body()!!.translations) {
      translations[translation.locale] = translation.translations
    }
    if (translations.isEmpty()) {
      translations[Locale.US] = mapOf()
    }
  }

  fun getTranslationsStartingWith(locale: Locale, prefix: String): Map<String, String> {
    val translationsForLocale = translations[locale] ?: emptyMap()
    return translationsForLocale.filterKeys { it.startsWith(prefix) }
  }

  fun get(key: String, locale: Locale, fallback: Boolean = true): String {
    if (!translations.containsKey(locale) && locale != Locale.US) {
      return get(key, Locale.US, fallback)
    }

    val translationsForLocale = translations[locale] ?: emptyMap()
    if (translationsForLocale.containsKey(key)) {
      return replaceOtherLanguageKeys(translationsForLocale[key]!!, locale)
    }

    if (locale == Locale.US) {
      return if (fallback) {
        "N/A ($key)"
      } else {
        key
      }
    }

    return get(key, Locale.US, fallback)
  }

  fun getMaybeMultiLine(key: String, locale: Locale, fallback: Boolean = true): Array<String> {
    val translation = get(key, locale, fallback)
    return translation.split("\n").toTypedArray()
  }

  fun getMaybeMultiLine(key: String, locale: Locale, replacements: Array<out Any>): Array<String> {
    val translation = get(key, locale, replacements)
    return translation.split("\n").toTypedArray()
  }

  fun get(key: String, locale: Locale, replacements: Map<String, Any>): String {
    val values = mutableListOf<Any>()

    for (entry in replacements) {
      values.add(entry.key)
      values.add(entry.value)
    }

    return get(key, locale, values.toTypedArray())
  }

  fun get(key: String, locale: Locale, replacements: Array<*>): String {
    if (replacements.size % 2 != 0) {
      throw RuntimeException("replacements must be even at key $key")
    }

    var translated = get(key, locale, false)

    val keys = mutableListOf<String>()
    val values = mutableListOf<Any>()

    var isKey = true
    for (entry in replacements) {
      if (isKey) {
        keys.add(entry.toString())
      } else {
        if (entry != null) {
          values.add(entry)
        }
      }
      isKey = !isKey
    }

    for ((lastIndex, s) in keys.withIndex()) {
      translated = translated.replace("{$s}", Transformation.transform(values[lastIndex], locale))
    }

    return replaceOtherLanguageKeys(translated, locale)
  }

  private fun replaceOtherLanguageKeys(translated: String, locale: Locale, tries: Int = 0): String {
    var translated1 = translated
    for (word in translated.split(" ")) {
      if (!word.contains("{")) {
        continue
      }

      if (!word.contains("}")) {
        continue
      }
      val wordWithoutBrackets = word.substring(1, word.length - 1)
      val replaced = get(wordWithoutBrackets, locale, false)
      if (replaced == wordWithoutBrackets) {
        continue
      }

      translated1 = translated.replace(word, replaced)
    }

    if (translated1.contains("{") && translated1.contains("}") && tries < 5) {
      return replaceOtherLanguageKeys(translated1, locale, tries + 1)
    }

    return translated1
  }
}