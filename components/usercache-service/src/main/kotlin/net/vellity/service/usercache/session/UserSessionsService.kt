package net.vellity.service.usercache.session

import net.vellity.service.usercache.login.PlayerLogin
import net.vellity.utils.context.Context
import java.util.*

interface UserSessionsService {
  fun getCurrentSession(context: Context, player: UUID): UserSession

  fun getCurrentSessionsOnContext(context: Context): List<UserSession>

  fun updateCurrentServer(context: Context, player: UUID, server: String)

  fun updateCurrentProxy(context: Context, player: UUID, proxy: String)

  fun destroySession(context: Context, player: UUID)

  fun createSession(context: Context, player: UUID, login: PlayerLogin)

  fun keepSessionsAlive(context: Context, players: List<UUID>)
}