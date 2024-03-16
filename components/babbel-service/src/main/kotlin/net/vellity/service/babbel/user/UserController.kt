package net.vellity.service.babbel.user

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

interface UserController {
  @GetMapping("/user")
  fun getUser(
    @RequestParam("context") context: Context,
    @RequestParam("playerId") playerId: UUID
  ): ResponseEntity<Locale>

  @PostMapping("/user")
  fun updateLanguage(
    @RequestParam("context") context: Context,
    @RequestParam("playerId") playerId: UUID,
    @RequestParam("language") language: String
  ): ResponseEntity<Unit>
}