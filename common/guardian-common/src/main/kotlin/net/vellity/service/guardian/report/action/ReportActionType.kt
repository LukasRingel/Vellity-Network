package net.vellity.service.guardian.report.action

import net.vellity.service.guardian.report.ReportState

enum class ReportActionType {
  IN_PROGRESS,
  ACCEPTED,
  DENIED,
  OPEN;

  companion object {
    fun byId(id: Int): ReportActionType {
      return when (id) {
        0 -> IN_PROGRESS
        1 -> ACCEPTED
        2 -> DENIED
        3 -> OPEN
        else -> throw IllegalArgumentException("Unknown report action type id: $id")
      }
    }

    fun forUpdatedState(state: ReportState): ReportActionType {
      return when (state) {
        ReportState.IN_PROGRESS -> IN_PROGRESS
        ReportState.ACCEPTED -> ACCEPTED
        ReportState.DENIED -> DENIED
        ReportState.OPEN -> OPEN
        ReportState.UNKNOWN -> IN_PROGRESS
      }
    }
  }
}