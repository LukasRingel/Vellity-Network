package net.vellity.service.babbel.user.mysql

import net.vellity.service.babbel.LocaleConverter
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectLocaleForUser(private val context: Context, private val playerId: UUID) : SelectQuery<Locale> {
  override fun query(): String {
    return "SELECT locale FROM babbel_users_locales WHERE player_id = ? and context = ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, playerId)
    preparedStatement.setContext(2, context)
  }

  override fun mapper(resultSet: ResultSet): Locale {
    return LocaleConverter.localeFromString(resultSet.getString("locale"))
  }
}