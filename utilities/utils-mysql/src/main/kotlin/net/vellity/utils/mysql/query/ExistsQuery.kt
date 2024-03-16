package net.vellity.utils.mysql.query

import java.sql.PreparedStatement

interface ExistsQuery {
  fun query(): String

  fun replace(preparedStatement: PreparedStatement)
}