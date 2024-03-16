package net.vellity.service.worker.history.mysql

import net.vellity.service.worker.WorkerRun
import net.vellity.service.worker.WorkerRunResult
import net.vellity.service.worker.history.WorkerHistoryRepository
import net.vellity.utils.mysql.MySqlConnection
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class MySqlWorkerHistoryRepository(private val mySqlConnection: MySqlConnection) : WorkerHistoryRepository {
  override fun getRecentRunsByIdentifier(identifier: String, limit: Int): List<WorkerRun> {
    if (!doesTableExist(identifier)) {
      return emptyList()
    }
    return mySqlConnection.executeQuery(GetRecentRuns(identifier, limit))
  }

  override fun createRun(worker: String, result: WorkerRunResult, startedAt: Instant, finishedAt: Instant): WorkerRun {
    createTableIfNeeded(worker)
    val id = mySqlConnection.executeUpdateAndGetId(InsertRun(worker, result, startedAt, finishedAt))
    return WorkerRun(id, worker, startedAt, finishedAt, result)
  }

  private fun doesTableExist(identifier: String): Boolean {
    return mySqlConnection.hasResults(TableExistsQuery(identifier))
  }

  private fun createTableIfNeeded(identifier: String) {
    mySqlConnection.executeUpdate(CreateTable(identifier))
  }
}