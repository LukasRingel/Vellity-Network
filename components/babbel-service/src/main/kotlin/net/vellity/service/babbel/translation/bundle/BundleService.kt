package net.vellity.service.babbel.translation.bundle

import net.vellity.service.babbel.Bundle
import net.vellity.service.babbel.BundleTranslations
import net.vellity.service.babbel.ColorConfiguration
import net.vellity.service.babbel.LocaleConverter
import net.vellity.utils.context.Context
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.util.*

@Service
class BundleService {

  private var bundles: MutableMap<Context, Bundle> = mutableMapOf()

  fun getBundle(context: Context): Bundle {
    return bundles[context] ?: Bundle(
      context,
      ColorConfiguration(mutableMapOf()),
      mutableSetOf()
    )
  }

  fun update(path: File) {
    for (context in Context.values()) {
      val bundle = scanDirectoryForContext(context, File(path, context.name.lowercase()))
      bundles.remove(context)
      bundles[context] = bundle
    }
  }

  private fun scanDirectoryForContext(context: Context, path: File): Bundle {
    val bundle = Bundle(
      context,
      ColorConfiguration(mutableMapOf()),
      mutableSetOf()
    )

    Files.walkFileTree(
      path.toPath(),
      DirectoryReader { file ->
        Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8).use { reader ->
          if (isColorConfigurationFile(file)) {
            loadColors(reader, bundle)
            return@DirectoryReader
          }

          val locale = LocaleConverter.localeFromFileName(file, "properties")

          if (bundle.translations.any { it.locale ==  locale}) {
            loadTranslationsToExistingTranslations(locale, bundle, reader)
          } else {
            loadTranslationsToNewTranslations(file, reader, bundle)
          }
        }
      })

    return bundle
  }

  private fun loadTranslationsToNewTranslations(file: File, reader: BufferedReader?, bundle: Bundle) {
    val translationBundle = BundleTranslations.empty(
      LocaleConverter.localeFromFileName(
        file, "properties"
      )
    )
    appendTranslationsToBundle(reader, translationBundle)
    bundle.translations = bundle.translations.plus(translationBundle)
  }

  private fun loadTranslationsToExistingTranslations(locale: Locale, bundle: Bundle, reader: BufferedReader?) {
    val translations = bundle.translations.first { it.locale == locale }
    appendTranslationsToBundle(reader, translations)
  }

  private fun isColorConfigurationFile(file: File) =
    file.nameWithoutExtension.equals("colors", true)

  private fun appendTranslationsToBundle(reader: BufferedReader?, translationBundle: BundleTranslations) {
    val properties = Properties()
    properties.load(reader)
    properties.forEach { key, value ->
      translationBundle.translations = translationBundle.translations.plus(
        Pair(
          key.toString(), value.toString()
        )
      )
    }
  }

  private fun loadColors(reader: BufferedReader?, bundle: Bundle) {
    val properties = Properties()
    properties.load(reader)
    val colors = mutableMapOf<String, String>()
    properties.forEach { key, value ->
      colors[key.toString()] = value.toString()
    }
    bundle.colorConfiguration = ColorConfiguration(colors)
  }

  companion object {
    private val logger = LoggerFactory.getLogger(BundleService::class.java)
  }
}