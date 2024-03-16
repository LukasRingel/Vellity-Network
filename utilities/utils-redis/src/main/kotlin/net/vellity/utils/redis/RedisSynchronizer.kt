package net.vellity.utils.redis

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class RedisSynchronizer(
  private val redisConnection: RedisConnection,
  private val key: String,
  private val updateDetected: () -> Unit
) {

  private var currentState: String = ""

  init {
    updateState()
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
      if (isUpdateDeployed()) {
        updateDetected()
        updateState()
      }
    }, 0, 1, TimeUnit.SECONDS)
  }

  fun deployUpdate() {
    redisConnection.asyncCommands().set(redisKey(), generateNewState())
  }

  fun isUpdateDeployed(): Boolean {
    return redisConnection.syncCommands().get(redisKey()) != currentState
  }

  fun updateState() {
    if (redisConnection.syncCommands().exists(redisKey())?.toInt() == 0) {
      currentState = generateNewState()
      redisConnection.asyncCommands().set(redisKey(), currentState)
      return
    }
    currentState = redisConnection.syncCommands().get(redisKey())
  }

  private fun redisKey(): String {
    return PREFIX + key
  }

  companion object {
    fun generateNewState(): String {
      return System.currentTimeMillis().toString()
    }

    const val PREFIX: String = "network:synchronizer:"
  }
}