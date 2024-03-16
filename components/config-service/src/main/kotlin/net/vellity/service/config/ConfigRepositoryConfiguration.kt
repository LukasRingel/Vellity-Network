package net.vellity.service.config

data class ConfigRepositoryConfiguration(
  val url: String,
  val username: String,
  val password: String,
  val branch: String,
  val localPath: String,
)