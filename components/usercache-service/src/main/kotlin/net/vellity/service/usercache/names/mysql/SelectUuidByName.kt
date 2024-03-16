package net.vellity.service.usercache.names.mysql

import net.vellity.utils.mysql.extensions.getUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectUuidByName(private val name: String) : SelectQuery<UUID> {
  override fun query(): String {
    return "select uuid from usercache_users_names where name = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setString(1, name)
  }

  override fun mapper(resultSet: ResultSet): UUID {
    return resultSet.getUuid("uuid")
  }
}