package net.vellity.service.usercache.login

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ServiceLoginController(private val loginService: LoginService) : LoginController {
  override fun getLastLogins(context: Context, player: UUID, limit: Int): ResponseEntity<List<PlayerLogin>> {
    return ResponseEntity.ok(loginService.getLastLogins(context, player, limit))
  }

  override fun registerLogin(context: Context, player: UUID, address: String): ResponseEntity<Unit> {
    loginService.registerLogin(context, player, address)
    return ResponseEntity.ok().build()
  }

  override fun registerLogout(context: Context, player: UUID): ResponseEntity<Unit> {
    loginService.registerLogout(context, player)
    return ResponseEntity.ok().build()
  }
}