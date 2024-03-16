package net.vellity.service.guardian.chatlog.mysql

import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement

class DeleteChatlog(private val id: Int): UpdateQuery {
  override fun query(): String =
    "update guardian_chatlogs set activeUntil = now() where id = ?"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setInt(1, id)
  }
}