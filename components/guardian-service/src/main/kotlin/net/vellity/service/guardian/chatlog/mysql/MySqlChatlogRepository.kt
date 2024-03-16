package net.vellity.service.guardian.chatlog.mysql

import net.vellity.service.guardian.chatlog.Chatlog
import net.vellity.service.guardian.chatlog.ChatlogRepository
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.extensions.nowUtc
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class MySqlChatlogRepository(private val mySqlConnection: MySqlConnection) : ChatlogRepository {
  override fun getChatlogWithId(id: Int): Chatlog? =
    mySqlConnection.executeQuery(SelectChatlogWithId(id)).firstOrNull()

  override fun createChatlog(context: Context, creator: UUID, target: UUID, activeUntil: Instant): Chatlog {
    val id = mySqlConnection.executeUpdateAndGetId(InsertChatlog(context, creator, target, activeUntil))
    return Chatlog(
      id = id,
      context = context,
      creator = creator,
      target = target,
      createdAt = nowUtc(),
      activeUntil = activeUntil
    )
  }

  override fun deleteChatlog(id: Int) =
    mySqlConnection.executeUpdate(DeleteChatlog(id))

  override fun getChatlogByTarget(context: Context, target: UUID): List<Chatlog> =
    mySqlConnection.executeQuery(SelectChatlogsForTarget(context, target))

  override fun mapMessagesToChatlog(chatlog: Chatlog, messages: List<Int>) =
    mySqlConnection.executeUpdate(InsertMessagesToChatlog(chatlog.id, messages))

  override fun getMessagesForChatlog(id: Int): List<Int> =
    mySqlConnection.executeQuery(SelectMessagesForChatlog(id))
}