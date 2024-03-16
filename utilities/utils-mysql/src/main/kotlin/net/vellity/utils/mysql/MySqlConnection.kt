package net.vellity.utils.mysql

import net.vellity.utils.mysql.migration.Migration
import net.vellity.utils.mysql.query.ExistsQuery
import net.vellity.utils.mysql.query.SelectQuery
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

interface MySqlConnection {
  fun <T> executeQuery(query: SelectQuery<T>): List<T>

  fun <T> executeQuery(query: String, mapper: (ResultSet) -> T): List<T>

  fun <T> executeQuery(query: String, replacements: (PreparedStatement) -> Unit, mapper: (ResultSet) -> T): List<T>

  fun executeUpdate(updateQuery: UpdateQuery)

  fun executeUpdate(query: String)

  fun executeUpdate(query: String, replacements: (PreparedStatement) -> Unit)

  fun executeUpdateAndGetId(updateQuery: UpdateQuery): Int

  fun executeUpdateAndGetId(query: String): Int

  fun executeUpdateAndGetId(query: String, replacements: (PreparedStatement) -> Unit): Int

  fun hasResults(query: ExistsQuery): Boolean

  fun runMigration(migration: Migration)

  fun runMigration(clazz: Class<out Migration>)

  fun connect()

  fun close()
}