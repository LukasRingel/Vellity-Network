package net.vellity.service.babbel.user.mysql

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.*

class UpdateLocaleForUser(private val context: Context, private val playerId: UUID, private val locale: Locale) :
  UpdateQuery {
  override fun query(): String {
    return "INSERT INTO babbel_users_locales (context, player_id, locale) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE locale = ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, playerId)
    preparedStatement.setString(3, locale.toString())
    preparedStatement.setString(4, locale.toString())
  }
}