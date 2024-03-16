package net.vellity.service.babbel.translation.repository

data class RepositoryConfiguration(
  val url: String,
  val username: String,
  val password: String,
  val branch: String,
  val localPath: String,
)