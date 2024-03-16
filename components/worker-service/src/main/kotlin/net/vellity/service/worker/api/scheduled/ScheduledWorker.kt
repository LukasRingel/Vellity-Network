package net.vellity.service.worker.api.scheduled

import net.vellity.service.worker.api.Worker
import net.vellity.utils.mysql.MySqlConnection
import java.util.concurrent.TimeUnit

abstract class ScheduledWorker(private val name: String, val timeUnitAmount: Long, val timeUnit: TimeUnit) :
  Worker {
  override fun identifier(): String = name

  abstract fun run()
}