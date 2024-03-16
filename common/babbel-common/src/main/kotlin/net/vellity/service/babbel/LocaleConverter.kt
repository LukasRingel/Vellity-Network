package net.vellity.service.babbel

import java.io.File
import java.util.*

class LocaleConverter {
  companion object {

    fun localeFromFileName(file: File, type: String): Locale {
      return localeFromString(file.name.replace(".$type", ""))
    }

    fun localeToString(locale: Locale): String {
      val localeString = locale.toString()

      if (localeString.contains("_")) {
        return localeString
      }

      return localeString + "_" + localeString.uppercase()
    }

    fun localeFromString(name: String): Locale {
      val segments = name.split("_")

      if (segments.size == 1) {
        return Locale(segments[0])
      }

      if (segments.size == 2) {
        return Locale(segments[0], segments[1])
      }

      if (segments.size == 3) {
        return Locale(segments[0], segments[1], segments[2])
      }

      return Locale.US
    }

  }
}