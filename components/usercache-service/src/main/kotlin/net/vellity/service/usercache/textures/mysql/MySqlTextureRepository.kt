package net.vellity.service.usercache.textures.mysql

import net.vellity.service.usercache.texture.PlayerTexture
import net.vellity.service.usercache.texture.PlayerTextureHistoryEntry
import net.vellity.service.usercache.textures.TextureRepository
import net.vellity.utils.mysql.MySqlConnection
import org.springframework.stereotype.Component
import java.util.*

@Component
class MySqlTextureRepository(private val mySqlConnection: MySqlConnection) : TextureRepository {
  override fun getTextureByUuid(player: UUID): PlayerTexture {
    return mySqlConnection.executeQuery(SelectTextureByUuid(player.toString())).firstOrNull() ?: PlayerTexture(
      player,
      "",
      ""
    )
  }

  override fun getTextureHistory(player: UUID): List<PlayerTextureHistoryEntry> {
    return mySqlConnection.executeQuery(SelectTextureHistory(player))
  }

  override fun getTexturesByUuids(players: List<UUID>): List<PlayerTexture> {
    return mySqlConnection.executeQuery(BulkSelectTexturesByUuid(players))
  }

  override fun insertOrUpdateTexture(player: UUID, signature: String, value: String) {
    mySqlConnection.executeUpdate(InsertOrUpdateTexture(player, signature, value))
  }

  override fun insertHistoryEntry(player: UUID, signature: String, value: String) {
    mySqlConnection.executeUpdate(InsertHistoryEntry(player, signature, value))
  }
}