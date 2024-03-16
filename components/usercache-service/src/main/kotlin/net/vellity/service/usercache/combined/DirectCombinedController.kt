package net.vellity.service.usercache.combined

import net.vellity.service.usercache.login.LoginService
import net.vellity.service.usercache.names.UserNameService
import net.vellity.service.usercache.session.UserSessionsService
import net.vellity.service.usercache.textures.TextureService
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class DirectCombinedController(
  private val loginService: LoginService,
  private val textureService: TextureService,
  private val nameService: UserNameService,
  private val userSessionsService: UserSessionsService
) : CombinedController {
  override fun handleLoginWithDetails(
    context: Context,
    player: UUID,
    name: String,
    proxyServer: String,
    ip: String,
    textureSignature: String,
    textureValue: String
  ): ResponseEntity<Unit> {
    nameService.checkForNameChange(player, name)
    textureService.checkForTextureChange(player, textureSignature, textureValue)
    loginService.registerLogin(context, player, ip)
    userSessionsService.updateCurrentProxy(context, player, proxyServer)
    return ResponseEntity.ok().build()
  }

  override fun bulkGetTexturesAndName(players: List<UUID>): ResponseEntity<List<TextureAndName>> {
    val textures = textureService.getTexturesForUuids(players)
    val names = nameService.getNamesByUuids(players)
    return ResponseEntity.ok(players.map { player ->
      val texture = textures.find { it.player == player }
      val name = names[player] ?: ""
      TextureAndName(player, name, texture?.signature ?: "", texture?.value ?: "")
    })
  }
}