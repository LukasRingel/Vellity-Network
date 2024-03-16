package net.vellity.service.babbel.translation.repository

import net.vellity.service.babbel.translation.bundle.BundleService
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.merge.MergeStrategy
import org.eclipse.jgit.transport.CredentialsProvider
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.slf4j.LoggerFactory
import java.io.File

class GitTranslationRepository(
  private val configuration: RepositoryConfiguration,
  private val bundleService: BundleService
): TranslationRepository {

  private val credentialsProvider: CredentialsProvider = UsernamePasswordCredentialsProvider(
    configuration.username,
    configuration.password
  )

  private val localPath: File = File(configuration.localPath)

  override fun cloneRepo() {
    Git.cloneRepository()
      .setURI(configuration.url)
      .setDirectory(localPath)
      .setBranch(configuration.branch)
      .setCredentialsProvider(credentialsProvider)
      .call()
  }

  override fun existsLocally(): Boolean {
    return localPath.exists()
  }

  override fun pull() {
    try {
      Git.open(localPath).use { git ->
        logger.info("Pulling latest locale-changes from remote repository (${configuration.branch})")
        git.pull()
          .setRemoteBranchName(configuration.branch)
          .setCredentialsProvider(credentialsProvider)
          .setStrategy(MergeStrategy.THEIRS)
          .call()
        bundleService.update(localPath)
      }
    } catch (e: Exception) {
      logger.error("Failed to pull latest locale-changes from remote repository (${configuration.branch})", e)
      return
    }
  }

  override fun anyUpdatesOnRemote(): Boolean {
    Git.open(localPath).use { git ->
      return git.fetch()
        .setCredentialsProvider(credentialsProvider)
        .call()
        .trackingRefUpdates
        .isNotEmpty()
    }
  }

  companion object {
    private val logger = LoggerFactory.getLogger(GitTranslationRepository::class.java)
  }
}