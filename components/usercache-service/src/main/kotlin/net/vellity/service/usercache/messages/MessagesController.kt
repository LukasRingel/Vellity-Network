package net.vellity.service.usercache.messages

import net.vellity.service.usercache.message.Message
import net.vellity.service.usercache.message.MessageSentStatus
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

interface MessagesController {
  @GetMapping("/messages")
  fun getMessage(
    @RequestParam("id") id: Int
  ): ResponseEntity<Message>

  @PutMapping("/messages/bulk")
  fun getMessages(
    @RequestBody ids: List<Int>
  ): ResponseEntity<List<Message>>

  @PostMapping("/messages")
  fun saveMessage(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("message") message: String,
    @RequestParam("status") sentStatus: MessageSentStatus
  ): ResponseEntity<Unit>

  @GetMapping("/messages/last")
  fun getLastMessages(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("limit") limit: Int
  ): ResponseEntity<List<Message>>

  @GetMapping("/messages/between")
  fun getMessagesBetween(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("startDate") startDate: String,
    @RequestParam("endDate") endDate: String,
  ): ResponseEntity<List<Message>>
}