package net.vellity.service.worker.history.mysql

import net.vellity.utils.mysql.query.ExistsQuery
import java.sql.PreparedStatement

class TableExistsQuery(private val identifier: String): ExistsQuery {
  override fun query(): String =
    "SHOW TABLES LIKE 'worker_history_${identifier}'"

  override fun replace(preparedStatement: PreparedStatement) {
    // Nothing to do
  }
}