package net.vellity.service.babbel.translation.repository

interface TranslationRepository {
  fun cloneRepo()

  fun existsLocally(): Boolean

  fun pull()

  fun anyUpdatesOnRemote(): Boolean
}