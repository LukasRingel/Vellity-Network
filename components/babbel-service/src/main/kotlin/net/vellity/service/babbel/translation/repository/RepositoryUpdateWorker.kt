package net.vellity.service.babbel.translation.repository

import net.vellity.service.babbel.BabbelServiceConfiguration
import net.vellity.service.babbel.translation.bundle.BundleService
import net.vellity.utils.configuration.Environment
import org.springframework.stereotype.Component
import java.io.File

@Component
class RepositoryUpdateWorker(configuration: BabbelServiceConfiguration, bundleService: BundleService) {
  private var translationRepository: TranslationRepository

  init {
    translationRepository = if (!Environment.getAsBooleanOrDefault("BABBEL_REPOSITORY_PULL", false)) {
      LocalTranslationRepository(configuration.repository(), bundleService)
    } else {
      GitTranslationRepository(configuration.repository(), bundleService)
    }

    update()
    bundleService.update(File(configuration.repository().localPath))
    /**
     * Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
     *       { run() },
     *       0,
     *       30,
     *       TimeUnit.SECONDS
     *     )
     */
  }

  fun update() {
    if (!translationRepository.existsLocally()) {
      translationRepository.cloneRepo()
      return
    }

    if (translationRepository.anyUpdatesOnRemote()) {
      translationRepository.pull()
    }
  }
}