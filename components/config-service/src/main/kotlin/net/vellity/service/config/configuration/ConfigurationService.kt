package net.vellity.service.config.configuration

import net.vellity.utils.context.Context

interface ConfigurationService {
  fun getConfiguration(context: Context, path: String): String

  fun update()
}