package net.vellity.service.babbel.user.mysql

import net.vellity.service.babbel.user.UserRepository
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import org.springframework.stereotype.Component
import java.util.*

@Component
class MySqlUserRepository(private val mySqlConnection: MySqlConnection) : UserRepository {
  override fun localeOf(context: Context, playerId: UUID): Locale {
    val locales = mySqlConnection.executeQuery(SelectLocaleForUser(context, playerId))
    if (locales.isEmpty()) {
      return Locale.US
    }
    return locales.first()
  }

  override fun updateLocale(context: Context, playerId: UUID, locale: Locale) {
    mySqlConnection.executeUpdate(UpdateLocaleForUser(context, playerId, locale))
  }
}