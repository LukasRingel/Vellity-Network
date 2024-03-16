package net.vellity.service.guardian.chatlog.message

import net.vellity.service.guardian.chatlog.ChatlogMessage
import net.vellity.utils.context.Context
import java.util.*

interface ChatlogMessageProvider {
  fun getMessagesForChatlog(context: Context, target: UUID): List<ChatlogMessage>

  fun getMessagesForSavedChatlog(ids: List<Int>): List<ChatlogMessage>
}