package net.vellity.service.worker.worker

import net.vellity.service.worker.RestTriggeredWorkers
import net.vellity.service.worker.api.rest.RestTriggeredWorker
import net.vellity.service.worker.api.scheduled.ScheduledWorker
import net.vellity.utils.redis.RedisConnection
import net.vellity.utils.redis.RedisSynchronizer
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

class SynchronizerDeployUpdate {
  @Component
  class SynchronizerDeployUpdateScheduledTriggeredWorker(private val redisConnection: RedisConnection) :
    ScheduledWorker("synchronizer-deploy-update", 5, TimeUnit.MINUTES) {
    override fun run() = update(redisConnection)
  }

  @Component
  class SynchronizerDeployUpdateRestTriggeredWorker(private val redisConnection: RedisConnection) :
    RestTriggeredWorker(RestTriggeredWorkers.NETWORK_SYNCHRONIZER_UPDATE_ALL) {
    override fun run() = update(redisConnection)
  }

  companion object {
    private fun update(redisConnection: RedisConnection) {
      redisConnection.syncCommands().keys(RedisSynchronizer.PREFIX + "*").forEach { key ->
        redisConnection.syncCommands().set(key, RedisSynchronizer.generateNewState())
      }
    }
  }
}