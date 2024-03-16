package net.vellity.service.guardian.chatlog

import net.vellity.service.guardian.chatlog.message.ChatlogMessageProvider
import net.vellity.utils.context.Context
import net.vellity.utils.configuration.lifetimeDate
import org.springframework.stereotype.Service
import java.util.*

@Service
class DirectChatlogService(
  private val chatlogRepository: ChatlogRepository,
  private val messageProvider: ChatlogMessageProvider
) : ChatlogService {
  override fun getChatLog(id: Int): Chatlog =
    chatlogRepository.getChatlogWithId(id) ?: throw ChatlogNotFoundException()

  override fun getMessagesForChatlog(id: Int): List<ChatlogMessage> {
    val messagesForChatlog = chatlogRepository.getMessagesForChatlog(id)
    if (messagesForChatlog.isEmpty()) {
      return emptyList()
    }
    return messageProvider.getMessagesForSavedChatlog(chatlogRepository.getMessagesForChatlog(id))
  }

  override fun createChatLog(context: Context, creator: UUID, target: UUID): Chatlog {
    val chatlog = chatlogRepository.createChatlog(context, creator, target, lifetimeDate)
    chatlogRepository.mapMessagesToChatlog(
      chatlog,
      messageProvider.getMessagesForChatlog(context, target).map { it.id })
    return chatlog
  }

  override fun deleteChatLog(id: Int) =
    chatlogRepository.deleteChatlog(id)

  override fun getChatLogByTarget(context: Context, target: UUID): List<Chatlog> =
    chatlogRepository.getChatlogByTarget(context, target)
}