package net.vellity.service.config.configuration

import net.vellity.service.config.ConfigServiceConfiguration
import net.vellity.service.config.configuration.repository.ConfigurationRepository
import net.vellity.service.config.configuration.repository.DirectoryReader
import net.vellity.service.config.configuration.repository.GitConfigurationRepository
import net.vellity.service.config.configuration.repository.LocalConfigurationRepository
import net.vellity.utils.configuration.Environment
import net.vellity.utils.context.Context
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path

@Service
class CachedConfigurationService(private val configuration: ConfigServiceConfiguration) : ConfigurationService {

  private val configurationRepository: ConfigurationRepository = if (Environment.isLocalDevelopment()) {
    LocalConfigurationRepository(configuration.repository())
  } else {
    GitConfigurationRepository(configuration.repository())
  }

  private val configurationCache: MutableMap<String, String> = mutableMapOf()

  init {
    updateLocalRepository()
    updateCache()
  }

  private fun updateLocalRepository() {
    if (!configurationRepository.existsLocally()) {
      configurationRepository.cloneRepo()
      return
    }
    if (configurationRepository.anyUpdatesOnRemote()) {
      configurationRepository.pull()
    }
  }

  private fun updateCache() {
    configurationCache.clear()
    Files.walkFileTree(Path.of(configuration.repository().localPath), DirectoryReader { context, file ->
      configurationCache[buildKey(context, removeFileExtension(file.name))] =
        String(Files.readAllBytes(file.toPath()), Charsets.UTF_8)
    })
  }

  override fun getConfiguration(context: Context, path: String): String {
    return configurationCache[buildKey(context, path)] ?: throw RuntimeException("Configuration not found")
  }

  override fun update() {
    updateLocalRepository()
    updateCache()
  }

  private fun buildKey(context: Context, path: String): String {
    return context.name + "/" + path
  }

  private fun removeFileExtension(path: String): String {
    return path.substring(0, path.lastIndexOf("."))
  }
}