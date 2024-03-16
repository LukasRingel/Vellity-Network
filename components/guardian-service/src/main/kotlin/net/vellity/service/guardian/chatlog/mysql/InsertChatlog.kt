package net.vellity.service.guardian.chatlog.mysql

import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.time.Instant
import java.util.*

class InsertChatlog(
  private val context: Context,
  private val creator: UUID,
  private val target: UUID,
  private val activeUntil: Instant
) : UpdateQuery {
  override fun query(): String =
    "insert into guardian_chatlogs (context, creator, target, activeUntil) values (?, ?, ?, ?)"

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, creator)
    preparedStatement.setUuid(3, target)
    preparedStatement.setInstant(4, activeUntil)
  }
}