package net.vellity.service.usercache.textures

import net.vellity.service.usercache.texture.PlayerTexture
import net.vellity.service.usercache.texture.PlayerTextureHistoryEntry
import java.util.*

interface TextureRepository {
  fun getTextureByUuid(player: UUID): PlayerTexture

  fun getTextureHistory(player: UUID): List<PlayerTextureHistoryEntry>

  fun getTexturesByUuids(players: List<UUID>): List<PlayerTexture>

  fun insertOrUpdateTexture(player: UUID, signature: String, value: String)

  fun insertHistoryEntry(player: UUID, signature: String, value: String)
}