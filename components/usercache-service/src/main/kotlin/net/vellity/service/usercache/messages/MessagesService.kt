package net.vellity.service.usercache.messages

import net.vellity.service.usercache.message.Message
import net.vellity.service.usercache.message.MessageSentStatus
import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

interface MessagesService {
  fun getMessage(id: Int): Message

  fun getMessages(ids: List<Int>): List<Message>

  fun saveMessage(context: Context, player: UUID, message: String, sentStatus: MessageSentStatus)

  fun getLastMessages(context: Context, player: UUID, limit: Int): List<Message>

  fun getMessagesBetween(context: Context, player: UUID, startDate: Instant, endDate: Instant): List<Message>
}