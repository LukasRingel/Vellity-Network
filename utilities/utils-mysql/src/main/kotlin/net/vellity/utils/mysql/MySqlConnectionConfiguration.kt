package net.vellity.utils.mysql

data class MySqlConnectionConfiguration(
  val hostname: String,
  val port: Int,
  val database: String,
  val username: String,
  val password: String,
  val maxPoolSize: Int = 10,
)