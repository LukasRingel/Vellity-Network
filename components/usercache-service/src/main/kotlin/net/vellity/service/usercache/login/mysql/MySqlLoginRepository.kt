package net.vellity.service.usercache.login.mysql

import net.vellity.service.usercache.login.PlayerLogin
import net.vellity.service.usercache.login.LoginRepository
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.extensions.nowUtc
import org.springframework.stereotype.Component
import java.util.*

@Component
class MySqlLoginRepository(private val mySqlConnection: MySqlConnection): LoginRepository {
  override fun getLastLogins(context: Context, player: UUID, limit: Int): List<PlayerLogin> {
    return mySqlConnection.executeQuery(SelectLastLoginsByUuidAndContext(context, player, limit))
  }

  override fun registerLogin(context: Context, player: UUID, address: String): PlayerLogin {
    val id = mySqlConnection.executeUpdateAndGetId(InsertLogin(context, player, address))
    return PlayerLogin(id, context, player, address, nowUtc())
  }

  override fun registerLogout(context: Context, player: UUID, login: Int) {
    mySqlConnection.executeUpdate(InsertLogout(context, player, login))
  }
}