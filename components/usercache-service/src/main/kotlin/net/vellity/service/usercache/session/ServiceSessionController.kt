package net.vellity.service.usercache.session

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ServiceSessionController(private val sessionsService: UserSessionsService): UserSessionController {
  override fun getCurrentSession(context: Context, player: UUID): ResponseEntity<UserSession> {
    return ResponseEntity.ok(sessionsService.getCurrentSession(context, player))
  }

  override fun getCurrentSessionsOnContext(context: Context): ResponseEntity<List<UserSession>> {
    return ResponseEntity.ok(sessionsService.getCurrentSessionsOnContext(context))
  }

  override fun updateCurrentServer(context: Context, player: UUID, server: String): ResponseEntity<Unit> {
    sessionsService.updateCurrentServer(context, player, server)
    return ResponseEntity.ok().build()
  }

  override fun updateCurrentProxy(context: Context, player: UUID, proxy: String): ResponseEntity<Unit> {
    sessionsService.updateCurrentProxy(context, player, proxy)
    return ResponseEntity.ok().build()
  }

  override fun keepSessionsAlive(context: Context, players: List<UUID>): ResponseEntity<Unit> {
    sessionsService.keepSessionsAlive(context, players)
    return ResponseEntity.ok().build()
  }
}