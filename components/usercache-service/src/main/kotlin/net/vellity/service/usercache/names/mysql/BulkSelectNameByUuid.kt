package net.vellity.service.usercache.names.mysql

import net.vellity.utils.mysql.extensions.getUuid
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class BulkSelectNameByUuid(private val uuids: List<UUID>) : SelectQuery<Pair<UUID, String>> {
  override fun query(): String {
    return "select * from usercache_users_names where uuid in (" + uuids.joinToString(",") { "?" } + ");"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    uuids.forEachIndexed { index, uuid ->
      preparedStatement.setUuid(index + 1, uuid)
      println("uuid: $uuid")
    }
  }

  override fun mapper(resultSet: ResultSet): Pair<UUID, String> {
    return Pair(resultSet.getUuid("uuid"), resultSet.getString("name"))
  }
}