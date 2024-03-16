package net.vellity.service.babbel

import net.vellity.utils.context.Context

data class Bundle(
  val context: Context,
  var colorConfiguration: ColorConfiguration,
  var translations: Set<BundleTranslations>
)