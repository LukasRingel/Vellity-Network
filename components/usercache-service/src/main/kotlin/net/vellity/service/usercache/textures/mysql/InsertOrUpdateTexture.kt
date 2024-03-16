package net.vellity.service.usercache.textures.mysql

import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.*

class InsertOrUpdateTexture(private val uuid: UUID, private val signature: String, private val value: String) :
  UpdateQuery {
  override fun query(): String {
    return "insert into usercache_users_textures (uuid, signature, value) values (?,?,?) on duplicate key update signature = ?, value = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, uuid)
    preparedStatement.setString(2, signature)
    preparedStatement.setString(3, value)
    preparedStatement.setString(4, signature)
    preparedStatement.setString(5, value)
  }
}