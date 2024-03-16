package net.vellity.service.usercache.textures

import net.vellity.service.usercache.texture.PlayerTexture
import net.vellity.service.usercache.texture.PlayerTextureHistoryEntry
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

interface TextureController {
  @GetMapping("/textures/current")
  fun getTextureByUuid(@RequestParam("player") player: UUID): ResponseEntity<PlayerTexture>

  @GetMapping("/textures/history")
  fun getTextureHistory(@RequestParam("player") player: UUID): ResponseEntity<List<PlayerTextureHistoryEntry>>

  @GetMapping("/textures/current/bulk")
  fun getTexturesForUuids(@RequestBody players: List<UUID>): ResponseEntity<List<PlayerTexture>>

  @PostMapping("/textures/check")
  fun checkForTextureChange(
    @RequestParam("player") player: UUID,
    @RequestParam("signature") signature: String,
    @RequestParam("texture") value: String
  ): ResponseEntity<Unit>
}