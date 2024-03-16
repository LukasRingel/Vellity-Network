package net.vellity.service.usercache.names.mysql

import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.*

class InsertHistoryEntry(private val player: UUID, private val name: String) : UpdateQuery {
  override fun query(): String {
    return "insert into usercache_users_names_history (player, name) values (?, ?);"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, player)
    preparedStatement.setString(2, name)
  }
}