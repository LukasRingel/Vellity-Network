package net.vellity.service.config.configuration.repository

import net.vellity.service.config.ConfigRepositoryConfiguration
import net.vellity.utils.context.Context
import java.io.File
import java.nio.file.Paths
import kotlin.io.path.createDirectories
import kotlin.io.path.exists

class LocalConfigurationRepository(
  private val configuration: ConfigRepositoryConfiguration,
) : ConfigurationRepository {

  override fun cloneRepo() {
    val path = Paths.get(configuration.localPath)
    if (!path.exists()) {
      path.createDirectories()
      val pathString = configuration.localPath + File.separator
      for (context in Context.values()) {
        File(pathString + context.name).mkdirs()
      }
    }
  }

  override fun existsLocally(): Boolean {
    return Paths.get(configuration.localPath).exists()
  }

  override fun pull() {
    // do nothing
  }

  override fun anyUpdatesOnRemote(): Boolean {
    // do nothing
    return false
  }
}