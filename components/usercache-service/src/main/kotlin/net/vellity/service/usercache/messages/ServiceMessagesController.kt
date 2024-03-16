package net.vellity.service.usercache.messages

import net.vellity.service.usercache.message.Message
import net.vellity.service.usercache.message.MessageSentStatus
import net.vellity.utils.context.Context
import net.vellity.utils.configuration.lifetimeDate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
class ServiceMessagesController(private val service: MessagesService) : MessagesController {
  override fun getMessage(id: Int): ResponseEntity<Message> =
    ResponseEntity.ok(service.getMessage(id))


  override fun getMessages(ids: List<Int>): ResponseEntity<List<Message>> =
    ResponseEntity.ok(service.getMessages(ids))


  override fun saveMessage(context: Context, player: UUID, message: String, sentStatus: MessageSentStatus): ResponseEntity<Unit> {
    service.saveMessage(context, player, message, sentStatus)
    return ResponseEntity.ok().build()
  }


  override fun getLastMessages(context: Context, player: UUID, limit: Int): ResponseEntity<List<Message>> =
    ResponseEntity.ok(service.getLastMessages(context, player, limit))


  override fun getMessagesBetween(
    context: Context,
    player: UUID,
    startDate: String,
    endDate: String
  ): ResponseEntity<List<Message>> {
    return ResponseEntity.ok(
      service.getMessagesBetween(
        context,
        player,
        stringToInstant(startDate),
        stringToInstant(endDate)
      )
    )
  }

  private fun stringToInstant(str: String?): Instant {
    if (str.isNullOrEmpty()) {
      return lifetimeDate
    }
    return if (isNumeric(str)) {
      Instant.ofEpochMilli(str.toLong())
    } else {
      Instant.parse(str)
    }
  }

  private fun isNumeric(str: String): Boolean {
    val sz = str.length
    for (i in 0 until sz) {
      if (!Character.isDigit(str[i])) {
        return false
      }
    }
    return true
  }
}