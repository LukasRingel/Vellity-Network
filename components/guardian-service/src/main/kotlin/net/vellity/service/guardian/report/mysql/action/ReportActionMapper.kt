package net.vellity.service.guardian.report.mysql.action

import net.vellity.service.guardian.report.action.ReportAction
import net.vellity.service.guardian.report.action.ReportActionType
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.getUuid
import java.sql.ResultSet

class ReportActionMapper {
  companion object {
    fun resultSetToAction(resultSet: ResultSet): ReportAction {
      return ReportAction(
        resultSet.getInt("id"),
        resultSet.getInt("reportId"),
        resultSet.getUuid("player"),
        ReportActionType.byId(resultSet.getInt("action")),
        resultSet.getInstant("createdAt")
      )
    }
  }
}