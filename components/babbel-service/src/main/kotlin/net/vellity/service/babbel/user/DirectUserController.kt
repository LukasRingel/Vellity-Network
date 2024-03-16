package net.vellity.service.babbel.user

import net.vellity.service.babbel.LocaleConverter
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class DirectUserController(private val userRepository: UserRepository) : UserController {
  override fun getUser(context: Context, playerId: UUID): ResponseEntity<Locale> {
    return ResponseEntity.ok(userRepository.localeOf(context, playerId))
  }

  override fun updateLanguage(context: Context, playerId: UUID, language: String): ResponseEntity<Unit> {
    userRepository.updateLocale(context, playerId, LocaleConverter.localeFromString(language))
    return ResponseEntity.ok().build()
  }
}