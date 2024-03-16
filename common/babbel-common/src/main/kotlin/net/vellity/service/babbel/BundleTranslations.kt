package net.vellity.service.babbel

import java.util.*

data class BundleTranslations(
  val locale: Locale,
  var translations: Map<String, String>
) {
  companion object {
    fun empty(locale: Locale): BundleTranslations {
      return BundleTranslations(locale, mutableMapOf())
    }
  }
}
