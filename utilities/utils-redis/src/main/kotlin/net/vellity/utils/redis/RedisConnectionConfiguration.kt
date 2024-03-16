package net.vellity.utils.redis

data class RedisConnectionConfiguration(
  val hostname: String,
  val port: Int,
  val password: String
)