package net.vellity.service.access.user.mysql.group

import net.vellity.utils.mysql.query.SelectQuery
import net.vellity.utils.mysql.extensions.getContext
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.setUuid
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectAllGroups(private val player: UUID) : SelectQuery<AssignedGroupRepresentation> {
  override fun query(): String {
    return "SELECT * FROM access_users_groups where player = ? and expireAt > now();"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, player)
  }

  override fun mapper(resultSet: ResultSet): AssignedGroupRepresentation {
    return AssignedGroupRepresentation(
      resultSet.getInt("id"),
      resultSet.getContext("context"),
      resultSet.getInstant("expireAt"),
      resultSet.getInt("group")
    )
  }
}