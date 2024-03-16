package net.vellity.service.worker

enum class WorkerRunResult {
  UNKNOWN,
  SUCCESS,
  FAILED;

  companion object {
    fun byId(ordinal: Int): WorkerRunResult = WorkerRunResult.values().firstOrNull { it.ordinal == ordinal } ?: UNKNOWN
  }
}