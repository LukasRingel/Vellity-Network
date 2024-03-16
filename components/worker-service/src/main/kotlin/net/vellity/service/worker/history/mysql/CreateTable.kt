package net.vellity.service.worker.history.mysql

import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class CreateTable(private val identifier: String) : UpdateQuery {
  override fun query(): String =
    "create table if not exists worker_history_${identifier} " +
      "( " +
      "    id         int auto_increment primary key, " +
      "    result     int       not null, " +
      "    startedAt  timestamp not null, " +
      "    finishedAt timestamp null " +
      ");"

  override fun replace(preparedStatement: PreparedStatement) {
    // nothing to do
  }
}