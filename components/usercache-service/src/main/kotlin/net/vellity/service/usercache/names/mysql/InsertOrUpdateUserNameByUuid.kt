package net.vellity.service.usercache.names.mysql

import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.UUID

class InsertOrUpdateUserNameByUuid(private val uuid: UUID, private val name: String): UpdateQuery {
  override fun query(): String {
    return "insert into usercache_users_names (uuid, name) values (?, ?) on duplicate key update name = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, uuid)
    preparedStatement.setString(2, name)
    preparedStatement.setString(3, name)
  }
}