package net.vellity.utils.audit.pool

import java.util.concurrent.Executors

private val auditPool = Executors.newCachedThreadPool()

fun submitAuditChange(runnable: Runnable) {
  auditPool.submit(runnable)
}