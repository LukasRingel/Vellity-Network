package net.vellity.utils.mysql.query

import java.sql.PreparedStatement

interface UpdateQuery {
  fun query(): String

  fun replace(preparedStatement: PreparedStatement)
}