package net.vellity.service.usercache.login

import net.vellity.utils.context.Context
import java.util.*

interface LoginRepository {
  fun getLastLogins(context: Context, player: UUID, limit: Int): List<PlayerLogin>

  fun registerLogin(context: Context, player: UUID, address: String): PlayerLogin

  fun registerLogout(context: Context, player: UUID, login: Int)
}