package net.vellity.service.guardian.chatlog

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ServiceChatlogController(private val service: ChatlogService) : ChatlogController {
  override fun getChatLog(id: Int): ResponseEntity<Chatlog> =
    ResponseEntity.ok(service.getChatLog(id))

  override fun getMessagesForChatlog(id: Int): ResponseEntity<List<ChatlogMessage>> =
    ResponseEntity.ok(service.getMessagesForChatlog(id))

  override fun createChatLog(context: Context, creator: UUID, target: UUID): ResponseEntity<Chatlog> =
    ResponseEntity.ok(service.createChatLog(context, creator, target))

  override fun deleteChatLog(id: Int): ResponseEntity<Unit> {
    service.deleteChatLog(id)
    return ResponseEntity.ok().build()
  }

  override fun getChatLogByTarget(context: Context, target: UUID): ResponseEntity<List<Chatlog>> =
    ResponseEntity.ok(service.getChatLogByTarget(context, target))
}