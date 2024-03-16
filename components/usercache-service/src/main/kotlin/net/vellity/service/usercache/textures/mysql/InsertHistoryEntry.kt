package net.vellity.service.usercache.textures.mysql

import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.*

class InsertHistoryEntry(private val player: UUID, private val signature: String, private val value: String) : UpdateQuery {
  override fun query(): String {
    return "insert into usercache_users_textures_history (player, signature, value) values (?,?,?);"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, player)
    preparedStatement.setString(2, signature)
    preparedStatement.setString(3, value)
  }
}