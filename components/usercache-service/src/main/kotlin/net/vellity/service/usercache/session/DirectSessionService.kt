package net.vellity.service.usercache.session

import net.vellity.service.usercache.login.PlayerLogin
import net.vellity.utils.context.Context
import org.springframework.stereotype.Service
import java.util.*

@Service
class DirectSessionService(private val repository: UserSessionRepository): UserSessionsService {
  override fun getCurrentSession(context: Context, player: UUID): UserSession {
    return repository.getCurrentSession(context, player)
  }

  override fun getCurrentSessionsOnContext(context: Context): List<UserSession> {
    return repository.getCurrentSessionsOnContext(context)
  }

  override fun updateCurrentServer(context: Context, player: UUID, server: String) {
    repository.updateCurrentServer(context, player, server)
  }

  override fun updateCurrentProxy(context: Context, player: UUID, proxy: String) {
    repository.updateCurrentProxy(context, player, proxy)
  }

  override fun destroySession(context: Context, player: UUID) {
    repository.destroySession(context, player)
  }

  override fun createSession(context: Context, player: UUID, login: PlayerLogin) {
    repository.createSession(context, player, login)
  }

  override fun keepSessionsAlive(context: Context, players: List<UUID>) {
    repository.keepSessionsAlive(context, players)
  }
}