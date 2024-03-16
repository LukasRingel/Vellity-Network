package net.vellity.service.usercache.login.mysql

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.*

class InsertLogin(private val context: Context, private val player: UUID, private val address: String) : UpdateQuery {
  override fun query(): String {
    return "insert into usercache_users_logins (context, uuid, address) values (?, ?, ?)"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
    preparedStatement.setString(3, address)
  }
}