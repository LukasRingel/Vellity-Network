package net.vellity.minecraft.common

import net.vellity.utils.configuration.Environment
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ExplorerRegistrationWorker(private val hostname: String, private val port: Int) {
  init {
    Environment.notOnLocalDevelopmentEnvironment {
      Executors.newSingleThreadScheduledExecutor()
        .scheduleWithFixedDelay(this::registerOurself, 0, 1, TimeUnit.SECONDS)
    }
  }

  private fun registerOurself() {
    try {
      explorerClient.updateHearthBeat(identity.type, identity.id, context, hostname, port)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  companion object {
    fun create(hostname: String, port: Int): ExplorerRegistrationWorker {
      return ExplorerRegistrationWorker(hostname, port)
    }
  }
}