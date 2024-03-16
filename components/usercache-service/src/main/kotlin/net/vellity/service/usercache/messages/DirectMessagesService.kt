package net.vellity.service.usercache.messages

import net.vellity.service.usercache.message.Message
import net.vellity.service.usercache.message.MessageSentStatus
import net.vellity.service.usercache.message.UnknownMessageException
import net.vellity.utils.context.Context
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class DirectMessagesService(private val repository: MessagesRepository) : MessagesService {
  override fun getMessage(id: Int): Message =
    repository.getMessage(id) ?: throw UnknownMessageException()


  override fun getMessages(ids: List<Int>): List<Message> =
    repository.getMessages(ids)

  override fun saveMessage(context: Context, player: UUID, message: String, sentStatus: MessageSentStatus) {
    repository.saveMessage(context, player, message, sentStatus)
  }

  override fun getLastMessages(context: Context, player: UUID, limit: Int): List<Message> =
    repository.getLastMessages(context, player, limit)

  override fun getMessagesBetween(context: Context, player: UUID, startDate: Instant, endDate: Instant): List<Message> =
    repository.getMessagesBetween(context, player, startDate, endDate)

}