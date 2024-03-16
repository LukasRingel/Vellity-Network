package net.vellity.service.guardian.chatlog

import net.vellity.utils.context.Context
import java.util.*

interface ChatlogService {
  fun getChatLog(id: Int): Chatlog

  fun getMessagesForChatlog(id: Int): List<ChatlogMessage>

  fun createChatLog(context: Context, creator: UUID, target: UUID): Chatlog

  fun deleteChatLog(id: Int)

  fun getChatLogByTarget(context: Context, target: UUID): List<Chatlog>
}