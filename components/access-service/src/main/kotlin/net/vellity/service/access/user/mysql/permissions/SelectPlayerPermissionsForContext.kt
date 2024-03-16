package net.vellity.service.access.user.mysql.permissions

import net.vellity.service.access.user.AssignedPermission
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.query.SelectQuery
import net.vellity.utils.mysql.extensions.getContext
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setUuid
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

class SelectPlayerPermissionsForContext(private val playerId: UUID, private val context: Context) :
  SelectQuery<AssignedPermission> {
  override fun query(): String {
    return "SELECT * FROM access_users_permissions where player = ? and (context = 1 or context = ?) and expireAt > now();"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setUuid(1, playerId)
    preparedStatement.setContext(2, context)
  }

  override fun mapper(resultSet: ResultSet): AssignedPermission {
    return AssignedPermission(
      resultSet.getInt("id"),
      resultSet.getContext("context"),
      resultSet.getString("permission"),
      resultSet.getInstant("expireAt"),
    )
  }
}