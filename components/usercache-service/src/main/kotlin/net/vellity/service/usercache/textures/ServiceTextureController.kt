package net.vellity.service.usercache.textures

import net.vellity.service.usercache.texture.PlayerTexture
import net.vellity.service.usercache.texture.PlayerTextureHistoryEntry
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ServiceTextureController(private val textureService: TextureService): TextureController {
  override fun getTextureByUuid(player: UUID): ResponseEntity<PlayerTexture> {
    return ResponseEntity.ok(textureService.getTextureByUuid(player))
  }

  override fun getTextureHistory(player: UUID): ResponseEntity<List<PlayerTextureHistoryEntry>> {
    return ResponseEntity.ok(textureService.getTextureHistory(player))
  }

  override fun getTexturesForUuids(players: List<UUID>): ResponseEntity<List<PlayerTexture>> {
    return ResponseEntity.ok(textureService.getTexturesForUuids(players))
  }

  override fun checkForTextureChange(player: UUID, signature: String, value: String): ResponseEntity<Unit> {
    textureService.checkForTextureChange(player, signature, value)
    return ResponseEntity.ok().build()
  }
}