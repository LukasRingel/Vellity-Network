package net.vellity.service.guardian.chatlog.message

import net.vellity.service.guardian.chatlog.ChatlogMessage
import net.vellity.service.usercache.UserCacheClient
import net.vellity.utils.configuration.Environment
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.nowUtc
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class UserCacheChatlogMessageProvider(private val userCacheClient: UserCacheClient) : ChatlogMessageProvider {
  override fun getMessagesForChatlog(context: Context, target: UUID): List<ChatlogMessage> {
    try {
      return userCacheClient.messages().getMessagesBetween(
        context,
        target,
        getChatlogMessageTimespan().toString(),
        nowUtc().toString()
      )
        .execute()
        .body()!!
        .map { message ->
          ChatlogMessage(
            message.id,
            message.player,
            message.message,
            message.createdAt
          )
        }
    } catch (e: Exception) {
      e.printStackTrace()
      return emptyList()
    }
  }

  override fun getMessagesForSavedChatlog(ids: List<Int>): List<ChatlogMessage> {
    try {
      return userCacheClient.messages().getMessages(ids)
        .execute()
        .body()!!
        .map { message ->
          ChatlogMessage(
            message.id,
            message.player,
            message.message,
            message.createdAt
          )
        }
    } catch (e: Exception) {
      e.printStackTrace()
      return emptyList()
    }
  }

  companion object {
    private val chatlogMessageTimespanMinutes = Environment.getAsLongOrDefault(
      "GUARDIAN_CHATLOG_TIMESPAN_MINUTES", 15
    )

    private fun getChatlogMessageTimespan(): Instant =
      nowUtc().minus(chatlogMessageTimespanMinutes, ChronoUnit.MINUTES)
  }
}