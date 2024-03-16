package net.vellity.service.usercache.textures

import net.vellity.service.usercache.texture.PlayerTexture
import net.vellity.service.usercache.texture.PlayerTextureHistoryEntry
import org.springframework.stereotype.Service
import java.util.*

@Service
class DirectTextureService(private val textureRepository: TextureRepository) : TextureService {
  override fun getTextureByUuid(player: UUID): PlayerTexture {
    return textureRepository.getTextureByUuid(player)
  }

  override fun getTextureHistory(player: UUID): List<PlayerTextureHistoryEntry> {
    return textureRepository.getTextureHistory(player)
  }

  override fun getTexturesForUuids(players: List<UUID>): List<PlayerTexture> {
    return textureRepository.getTexturesByUuids(players)
  }

  override fun checkForTextureChange(player: UUID, signature: String, value: String) {
    val textureByUuid = getTextureByUuid(player)
    if (textureByUuid.value == value) {
      return
    }

    textureRepository.insertOrUpdateTexture(player, signature, value)

    if (textureByUuid.value.isEmpty()) {
      return
    }

    textureRepository.insertHistoryEntry(player, signature, value)
  }
}