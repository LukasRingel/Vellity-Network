package net.vellity.utils.mysql.query

import java.sql.PreparedStatement
import java.sql.ResultSet

interface SelectQuery<T> {
  fun query(): String

  fun replace(preparedStatement: PreparedStatement)

  fun mapper(resultSet: ResultSet): T
}