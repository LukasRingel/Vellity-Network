package net.vellity.service.usercache.login

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

interface LoginController {
  @GetMapping("/logins")
  fun getLastLogins(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("limit") limit: Int
  ): ResponseEntity<List<PlayerLogin>>

  @PostMapping("/logins")
  fun registerLogin(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("address") address: String
  ): ResponseEntity<Unit>

  @PostMapping("/logout")
  fun registerLogout(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID
  ): ResponseEntity<Unit>
}