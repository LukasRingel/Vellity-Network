package net.vellity.service.usercache.combined

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

interface CombinedController {
  @PutMapping("/combined/login")
  fun handleLoginWithDetails(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("name") name: String,
    @RequestParam("proxyServer") proxyServer: String,
    @RequestParam("ip") ip: String,
    @RequestParam("textureSignature") textureSignature: String,
    @RequestParam("textureValue") textureValue: String
  ): ResponseEntity<Unit>

  @PutMapping("/combined/texturesAndName")
  fun bulkGetTexturesAndName(
    @RequestBody players: List<UUID>
  ): ResponseEntity<List<TextureAndName>>
}