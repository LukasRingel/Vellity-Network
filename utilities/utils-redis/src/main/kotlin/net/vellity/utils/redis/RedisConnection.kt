package net.vellity.utils.redis

import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.pubsub.RedisPubSubListener
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection

class RedisConnection(private val configuration: RedisConnectionConfiguration) {

  private var client: RedisClient? = null
  private var connection: StatefulRedisConnection<String, String>? = null
  private var pubSubConnection: StatefulRedisPubSubConnection<String, String>? = null

  init {
    connect()
  }

  private fun connect() {
    val url = if (configuration.password.isEmpty()) {
      "redis://${configuration.hostname}:${configuration.port}"
    } else {
      "redis://${configuration.password}@${configuration.hostname}:${configuration.port}"
    }

    client = RedisClient.create(url)
    connection = client!!.connect()
    pubSubConnection = client!!.connectPubSub()
  }

  fun isConnected() = connection!!.isOpen

  fun syncCommands() = connection!!.sync()

  fun asyncCommands() = connection!!.async()

  fun pubSubCommands() = pubSubConnection!!.sync()

  fun pubSubAsyncCommands() = pubSubConnection!!.async()

  fun addListener(listener: RedisPubSubListener<String, String>) {
    pubSubConnection!!.addListener(listener)
  }

  fun close() {
    connection!!.close()
    pubSubConnection!!.close()
    client!!.shutdown()
  }

}