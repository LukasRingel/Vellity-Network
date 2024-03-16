package net.vellity.service.usercache.login.mysql

import net.vellity.service.usercache.login.PlayerLogin
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.*
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectLastLoginsByUuidAndContext(private val context: Context, private val player: UUID, private val limit: Int) :
  SelectQuery<PlayerLogin> {
  override fun query(): String {
    return "select * from usercache_users_logins where context = ? and uuid = ? order by date desc limit ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
    preparedStatement.setInt(3, limit)
  }

  override fun mapper(resultSet: ResultSet): PlayerLogin {
    return PlayerLogin(
      resultSet.getInt("id"),
      resultSet.getContext("context"),
      resultSet.getUuid("uuid"),
      resultSet.getString("address"),
      resultSet.getInstant("date")
    )
  }
}