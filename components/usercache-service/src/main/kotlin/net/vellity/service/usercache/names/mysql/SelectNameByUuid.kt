package net.vellity.service.usercache.names.mysql

import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectNameByUuid(private val uuid: UUID) : SelectQuery<String> {
  override fun query(): String {
    return "select name from usercache_users_names where uuid = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, uuid)
  }

  override fun mapper(resultSet: ResultSet): String {
    return resultSet.getString("name")
  }

}