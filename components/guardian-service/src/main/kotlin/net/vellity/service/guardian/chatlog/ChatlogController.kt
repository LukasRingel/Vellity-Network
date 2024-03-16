package net.vellity.service.guardian.chatlog

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

interface ChatlogController {
  @GetMapping("/chatlog")
  fun getChatLog(
    @RequestParam("id") id: Int
  ): ResponseEntity<Chatlog>

  @GetMapping("/chatlog/messages")
  fun getMessagesForChatlog(
    @RequestParam("id") id: Int
  ): ResponseEntity<List<ChatlogMessage>>

  @PostMapping("/chatlog")
  fun createChatLog(
    @RequestParam("context") context: Context,
    @RequestParam("creator") creator: UUID,
    @RequestParam("target") target: UUID
  ): ResponseEntity<Chatlog>

  @DeleteMapping("/chatlog")
  fun deleteChatLog(
    @RequestParam("id") id: Int
  ): ResponseEntity<Unit>

  @GetMapping("/chatlog/target")
  fun getChatLogByTarget(
    @RequestParam("context") context: Context,
    @RequestParam("target") target: UUID
  ): ResponseEntity<List<Chatlog>>
}