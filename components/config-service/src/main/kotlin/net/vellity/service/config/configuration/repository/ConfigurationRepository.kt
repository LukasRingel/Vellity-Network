package net.vellity.service.config.configuration.repository

interface ConfigurationRepository {
  fun cloneRepo()

  fun existsLocally(): Boolean

  fun pull()

  fun anyUpdatesOnRemote(): Boolean
}