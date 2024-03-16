package net.vellity.service.usercache.textures

import net.vellity.service.usercache.texture.PlayerTexture
import net.vellity.service.usercache.texture.PlayerTextureHistoryEntry
import java.util.*

interface TextureService {
  fun getTextureByUuid(player: UUID): PlayerTexture

  fun getTextureHistory(player: UUID): List<PlayerTextureHistoryEntry>

  fun getTexturesForUuids(players: List<UUID>): List<PlayerTexture>

  fun checkForTextureChange(player: UUID, signature: String, value: String)
}