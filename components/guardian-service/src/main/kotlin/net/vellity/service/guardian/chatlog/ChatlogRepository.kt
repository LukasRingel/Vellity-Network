package net.vellity.service.guardian.chatlog

import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

interface ChatlogRepository {
  fun getChatlogWithId(id: Int): Chatlog?

  fun createChatlog(context: Context, creator: UUID, target: UUID, activeUntil: Instant): Chatlog

  fun deleteChatlog(id: Int)

  fun getChatlogByTarget(context: Context, target: UUID): List<Chatlog>

  fun mapMessagesToChatlog(chatlog: Chatlog, messages: List<Int>)

  fun getMessagesForChatlog(id: Int): List<Int>
}