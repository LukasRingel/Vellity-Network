package net.vellity.service.friends.settings.mysql

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.UUID

class SelectSettingsForPlayer(private val context: Context, private val player: UUID): SelectQuery<Pair<Int, Int>> {
  override fun query(): String {
    return "select * from friends_users_settings where context = ? and player = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
  }

  override fun mapper(resultSet: ResultSet): Pair<Int, Int> {
    return Pair(resultSet.getInt("setting"), resultSet.getInt("status"))
  }
}