package net.vellity.service.friends.friendship.mysql.blocklist

import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant

class UpdateActiveUntilForBlocklistEntry(private val id: Int, private val activeUntil: Instant): UpdateQuery {
  override fun query(): String {
    return "update friends_users_blocked set activeUntil = ? where id = ?"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInstant(1, activeUntil)
    preparedStatement.setInt(2, id)
  }
}