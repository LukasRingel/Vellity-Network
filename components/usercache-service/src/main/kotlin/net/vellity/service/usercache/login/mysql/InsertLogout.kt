package net.vellity.service.usercache.login.mysql

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.*

class InsertLogout(private val context: Context, private val player: UUID, private val login: Int) :
  UpdateQuery {
  override fun query(): String {
    return "insert into usercache_users_logouts (context, uuid, login) values (?, ?, ?)"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
    preparedStatement.setInt(3, login)
  }
}