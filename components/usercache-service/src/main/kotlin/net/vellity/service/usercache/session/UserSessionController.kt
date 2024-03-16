package net.vellity.service.usercache.session

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

interface UserSessionController {
  @GetMapping("/sessions")
  fun getCurrentSession(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID
  ): ResponseEntity<UserSession>

  @GetMapping("/sessions/context")
  fun getCurrentSessionsOnContext(
    @RequestParam("context") context: Context
  ): ResponseEntity<List<UserSession>>

  @PutMapping("/sessions/server")
  fun updateCurrentServer(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("server") server: String
  ): ResponseEntity<Unit>

  @PutMapping("/sessions/proxy")
  fun updateCurrentProxy(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("proxy") proxy: String
  ): ResponseEntity<Unit>

  @PostMapping("/sessions/keepalive")
  fun keepSessionsAlive(
    @RequestParam("context") context: Context,
    @RequestBody players: List<UUID>
  ): ResponseEntity<Unit>
}