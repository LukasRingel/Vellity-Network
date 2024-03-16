package net.vellity.service.worker.history.mysql

import net.vellity.service.worker.WorkerRunResult
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant

class InsertRun(
  private val identifier: String,
  private val result: WorkerRunResult,
  private val startedAt: Instant,
  private val finishedAt: Instant
) :
  UpdateQuery {
  override fun query(): String =
    "insert into worker_history_${identifier} (result, startedAt, finishedAt) values (?, ?, ?);"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, result.ordinal)
    preparedStatement.setInstant(2, startedAt)
    preparedStatement.setInstant(3, finishedAt)
  }
}