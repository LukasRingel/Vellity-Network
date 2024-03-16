package net.vellity.service.guardian.report

enum class ReportState {
  UNKNOWN,
  OPEN,
  IN_PROGRESS,
  ACCEPTED,
  DENIED;

  companion object {
    fun fromId(id: Int): ReportState {
      return values().find { it.ordinal == id } ?: UNKNOWN
    }
  }
}