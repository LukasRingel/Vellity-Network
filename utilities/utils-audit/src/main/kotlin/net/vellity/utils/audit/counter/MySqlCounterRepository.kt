package net.vellity.utils.audit.counter

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.extensions.setContext
import java.util.concurrent.ExecutorService

class MySqlCounterRepository(
  private val mySqlConnection: MySqlConnection,
  private val tableName: String,
  executorService: ExecutorService
) : CounterRepository {

  init {
    executorService.submit {
      mySqlConnection.executeUpdate("create table if not exists $tableName ( " +
        "context integer default 0 not null, " +
        "identifier varchar(48) not null, " +
        "count bigint default 0, " +
        "changedAt timestamp default current_timestamp not null, " +
        "constraint context_identifier unique (context, identifier));")
    }
  }

  override fun increaseCounter(context: Context, identifier: String) {
    mySqlConnection.executeUpdate(
      "insert into $tableName (context, identifier, count) values (?, ?, 1) on " +
        "duplicate key update count = count + 1;"
    ) { preparedStatement ->
      preparedStatement.setContext(1, context)
      preparedStatement.setString(2, identifier)
    }
  }

  override fun getCounter(context: Context, identifier: String): Int {
    return mySqlConnection.executeQuery("select count from $tableName where identifier = ? and context = ?;",
      { preparedStatement ->
        preparedStatement.setString(1, identifier)
        preparedStatement.setContext(2, context)
      },
      { resultSet -> resultSet.getInt("count") })
      .firstOrNull() ?: 0
  }
}