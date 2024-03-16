package net.vellity.utils.mysql.supplied

import net.vellity.utils.mysql.migration.Migration
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.MySqlConnectionConfiguration
import net.vellity.utils.mysql.log.QueryLogging
import net.vellity.utils.mysql.query.ExistsQuery
import net.vellity.utils.mysql.query.SelectQuery
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement

class SuppliedMySqlConnection(
  private val supplier: ConnectionSupplier,
  private val logging: QueryLogging
) : MySqlConnection {

  override fun <T> executeQuery(query: SelectQuery<T>): List<T> {
    return executeQuery(
      query.query(),
      { query.replace(it) },
      { query.mapper(it) }
    )
  }

  override fun <T> executeQuery(query: String, mapper: (ResultSet) -> T): List<T> {
    return executeQuery(query, {}, mapper)
  }

  override fun <T> executeQuery(
    query: String,
    replacements: (PreparedStatement) -> Unit,
    mapper: (ResultSet) -> T
  ): List<T> {
    try {
      val connection = supplier.get()
      val statement = connection.prepareStatement(query)
      replacements(statement)
      val resultSet = statement.executeQuery()
      val result = mutableListOf<T>()
      while (resultSet.next()) {
        result.add(mapper(resultSet))
      }
      resultSet.close()
      statement.close()
      logging.logQuery(query)
      supplier.finishConnection(connection)
      return result
    } catch (e: Exception) {
      logging.logQueryError(query)
      e.printStackTrace()
      return emptyList()
    }
  }

  override fun executeUpdate(updateQuery: UpdateQuery) {
    executeUpdate(updateQuery.query()) { updateQuery.replace(it) }
  }

  override fun executeUpdate(query: String) {
    val connection = supplier.get()
    val statement = connection.prepareStatement(query)
    try {
      statement.executeUpdate()
      logging.logQuery(query)
      statement.close()
      supplier.finishConnection(connection)
    } catch (e: Exception) {
      logging.logQueryError(query)
      statement.close()
      supplier.finishConnection(connection)
      e.printStackTrace()
    }
  }

  override fun executeUpdate(query: String, replacements: (PreparedStatement) -> Unit) {
    val connection = supplier.get()
    val statement = connection.prepareStatement(query)
    replacements(statement)
    try {
      statement.executeUpdate()
      statement.close()
      logging.logQuery(query)
    } catch (e: Exception) {
      logging.logQueryError(query)
      e.printStackTrace()
    } finally {
      statement.close()
      supplier.finishConnection(connection)
    }
  }

  override fun executeUpdateAndGetId(updateQuery: UpdateQuery): Int {
    return executeUpdateAndGetId(updateQuery.query()) { updateQuery.replace(it) }
  }

  override fun executeUpdateAndGetId(query: String): Int {
    val connection = supplier.get()
    val statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
    try {
      statement.executeUpdate()
    } catch (e: Exception) {
      logging.logQueryError(query)
      statement.close()
      supplier.finishConnection(connection)
      e.printStackTrace()
    }
    val resultSet = statement.generatedKeys
    resultSet.next()
    val id = resultSet.getInt(1)
    resultSet.close()
    statement.close()
    logging.logQuery(query)
    supplier.finishConnection(connection)
    return id
  }

  override fun executeUpdateAndGetId(query: String, replacements: (PreparedStatement) -> Unit): Int {
    val connection = supplier.get()
    val statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
    replacements(statement)
    try {
      statement.executeUpdate()
    } catch (e: Exception) {
      logging.logQueryError(query)
      statement.close()
      supplier.finishConnection(connection)
      e.printStackTrace()
    }
    val resultSet = statement.generatedKeys
    resultSet.next()
    val id = resultSet.getInt(1)
    resultSet.close()
    statement.close()
    logging.logQuery(query)
    supplier.finishConnection(connection)
    return id
  }

  override fun hasResults(query: ExistsQuery): Boolean {
    return executeQuery(query.query(), { query.replace(it) }, { it.next() }).isNotEmpty()
  }

  override fun runMigration(migration: Migration) {
    if (!migration.shouldRun()) {
      return
    }

    for (query in migration.queries()) {
      executeUpdate(query)
    }
  }

  override fun runMigration(clazz: Class<out Migration>) {
    clazz.getConstructor().newInstance().let { runMigration(it) }
  }

  override fun connect() {
    supplier.connect()
  }

  override fun close() {
    supplier.close()
  }
}