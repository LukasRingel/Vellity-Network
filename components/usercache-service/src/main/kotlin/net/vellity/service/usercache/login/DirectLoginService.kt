package net.vellity.service.usercache.login

import net.vellity.service.usercache.session.UserSessionsService
import net.vellity.utils.context.Context
import org.springframework.stereotype.Service
import java.util.*

@Service
class DirectLoginService(
  private val loginRepository: LoginRepository,
  private val sessionsService: UserSessionsService
) : LoginService {
  override fun getLastLogins(context: Context, player: UUID, limit: Int): List<PlayerLogin> {
    return loginRepository.getLastLogins(context, player, limit)
  }

  override fun registerLogin(context: Context, player: UUID, address: String) {
    val playerLogin = loginRepository.registerLogin(context, player, address)
    sessionsService.createSession(context, player, playerLogin)
  }

  override fun registerLogout(context: Context, player: UUID) {
    getLastLogins(context, player, 1).firstOrNull()?.let {
      loginRepository.registerLogout(context, player, it.id)
      sessionsService.destroySession(context, player)
    }
  }
}