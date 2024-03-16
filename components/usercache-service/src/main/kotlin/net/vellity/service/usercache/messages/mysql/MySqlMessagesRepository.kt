package net.vellity.service.usercache.messages.mysql

import net.vellity.service.usercache.message.Message
import net.vellity.service.usercache.message.MessageSentStatus
import net.vellity.service.usercache.messages.MessagesRepository
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.extensions.nowUtc
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class MySqlMessagesRepository(private val mySqlConnection: MySqlConnection) : MessagesRepository {
  override fun getMessage(id: Int): Message? =
    mySqlConnection.executeQuery(SelectMessageWithId(id)).firstOrNull()

  override fun getMessages(ids: List<Int>): List<Message> =
    mySqlConnection.executeQuery(SelectMessagesForIds(ids))

  override fun saveMessage(context: Context, player: UUID, message: String, sentStatus: MessageSentStatus): Message {
    val id = mySqlConnection.executeUpdateAndGetId(InsertMessage(context, player, message, sentStatus))
    return Message(id, player, context, message, sentStatus, nowUtc())
  }

  override fun getLastMessages(context: Context, player: UUID, limit: Int): List<Message> =
    mySqlConnection.executeQuery(SelectLastMessages(context, player, limit))

  override fun getMessagesBetween(context: Context, player: UUID, startDate: Instant, endDate: Instant): List<Message> =
    mySqlConnection.executeQuery(SelectMessagesBetween(context, player, startDate, endDate))
}