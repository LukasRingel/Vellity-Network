package net.vellity.service.config.configuration.repository

import net.vellity.service.config.ConfigRepositoryConfiguration
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.merge.MergeStrategy
import org.eclipse.jgit.transport.CredentialsProvider
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.slf4j.LoggerFactory
import java.io.File

class GitConfigurationRepository(
  private val configuration: ConfigRepositoryConfiguration
) : ConfigurationRepository {

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
        git.pull()
          .setRemoteBranchName(configuration.branch)
          .setCredentialsProvider(credentialsProvider)
          .setStrategy(MergeStrategy.THEIRS)
          .call()
      }
    } catch (e: Exception) {
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
}