package net.vellity.service.usercache.login

import net.vellity.utils.context.Context
import java.util.*

interface LoginService {
  fun getLastLogins(context: Context, player: UUID, limit: Int): List<PlayerLogin>

  fun registerLogin(context: Context, player: UUID, address: String)

  fun registerLogout(context: Context, player: UUID)
}