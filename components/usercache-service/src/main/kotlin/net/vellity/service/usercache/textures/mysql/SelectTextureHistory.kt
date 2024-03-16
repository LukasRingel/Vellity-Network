package net.vellity.service.usercache.textures.mysql

import net.vellity.service.usercache.texture.PlayerTextureHistoryEntry
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.getUuid
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

class SelectTextureHistory(private val player: UUID): SelectQuery<PlayerTextureHistoryEntry> {
  override fun query(): String {
    return "select * from usercache_users_textures_history where player = ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, player)
  }

  override fun mapper(resultSet: ResultSet): PlayerTextureHistoryEntry {
    return PlayerTextureHistoryEntry(
      resultSet.getUuid("player"),
      resultSet.getString("signature"),
      resultSet.getString("value"),
      resultSet.getInstant("changedAt")
    )
  }
}