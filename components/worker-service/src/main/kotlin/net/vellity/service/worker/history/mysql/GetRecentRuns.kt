package net.vellity.service.worker.history.mysql

import net.vellity.service.worker.WorkerRun
import net.vellity.service.worker.WorkerRunResult
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class GetRecentRuns(private val identifier: String, private val limit: Int) : SelectQuery<WorkerRun> {
  override fun query(): String =
    "select * from worker_history_${identifier} order by id desc limit ?;"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, limit)
  }

  override fun mapper(resultSet: ResultSet): WorkerRun =
    WorkerRun(
      id = resultSet.getInt("id"),
      worker = identifier,
      result = WorkerRunResult.byId(resultSet.getInt("result")),
      startedAt = resultSet.getInstant("startedAt"),
      finishedAt = resultSet.getInstant("finishedAt")
    )
}