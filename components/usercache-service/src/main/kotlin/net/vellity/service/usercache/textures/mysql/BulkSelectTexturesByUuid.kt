package net.vellity.service.usercache.textures.mysql

import net.vellity.service.usercache.texture.PlayerTexture
import net.vellity.utils.mysql.extensions.getUuid
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class BulkSelectTexturesByUuid(private val uuids: List<UUID>) : SelectQuery<PlayerTexture> {
  override fun query(): String {
    return "select * from usercache_users_textures where uuid in (" + uuids.joinToString(",") { "?" } + ");"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    uuids.forEachIndexed { index, uuid ->
      preparedStatement.setUuid(index + 1, uuid)
    }
  }

  override fun mapper(resultSet: ResultSet): PlayerTexture {
    return PlayerTexture(
      resultSet.getUuid("uuid"),
      resultSet.getString("signature"),
      resultSet.getString("value")
    )
  }
}