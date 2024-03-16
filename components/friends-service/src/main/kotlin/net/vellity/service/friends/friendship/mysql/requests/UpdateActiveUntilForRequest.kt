package net.vellity.service.friends.friendship.mysql.requests

import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant

class UpdateActiveUntilForRequest(private val friendshipId: Int, private val activeUntil: Instant) : UpdateQuery {
  override fun query(): String {
    return "update friends_users_requests set activeUntil = ? where id = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInstant(1, activeUntil)
    preparedStatement.setInt(2, friendshipId)
  }

}