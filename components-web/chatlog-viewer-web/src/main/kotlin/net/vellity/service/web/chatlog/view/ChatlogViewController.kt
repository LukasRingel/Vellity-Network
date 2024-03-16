package net.vellity.service.web.chatlog.view

import net.vellity.service.guardian.GuardianClient
import net.vellity.service.guardian.chatlog.Chatlog
import net.vellity.service.usercache.UserCacheClient
import net.vellity.service.usercache.name.NameResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import retrofit2.Response

@Controller
class ChatlogViewController(private val guardianClient: GuardianClient, private val userCacheClient: UserCacheClient) {

  @GetMapping("/view/{id}")
  fun viewChatlog(@PathVariable id: String, model: Model): String {
    val chatlogId = validateInput(id) ?: return ERROR_PAGE

    val chatlogResponse = requestChatlogData(chatlogId)
    if (!chatlogResponse.isSuccessful) {
      return ERROR_PAGE
    }
    val chatlog = chatlogResponse.body() ?: return ERROR_PAGE

    val namedResponse = requestNamesOfCreatorAndTarget(chatlog)
    if (!namedResponse.isSuccessful) {
      return ERROR_PAGE
    }
    val names = namedResponse.body() ?: return ERROR_PAGE

    val messagesResponse = guardianClient.chatlogs().getMessagesForChatlog(chatlogId).execute()
    if (!messagesResponse.isSuccessful) {
      return ERROR_PAGE
    }

    val messages = messagesResponse.body()!!

    model.addAttribute("chatlog_id", chatlog.id)
    model.addAttribute("chatlog_creator_name", names.find { it.uuid == chatlog.creator }?.name ?: "???")
    model.addAttribute("chatlog_creator_uuid", chatlog.creator)
    model.addAttribute("chatlog_target_name", names.find { it.uuid == chatlog.target }?.name ?: "???")
    model.addAttribute("chatlog_target_uuid", chatlog.target)
    model.addAttribute("chatlog_context", chatlog.context.name)
    model.addAttribute("chatlog_created", chatlog.createdAt.toString())
    model.addAttribute("chatlog_server", "???")

    model.addAttribute("target_playtime", "???")
    model.addAttribute("target_trustfactor", "???")

    model.addAttribute("chatlog_messages", messages)

    return "chatlog"
  }

  private fun requestChatlogData(chatlogId: Int): Response<Chatlog> =
    guardianClient.chatlogs().getChatLog(chatlogId).execute()

  private fun requestNamesOfCreatorAndTarget(chatlog: Chatlog): Response<List<NameResponse>> =
    userCacheClient.names()
      .getNamesByUuids(listOf(chatlog.target.toString(), chatlog.creator.toString()))
      .execute()

  private fun validateInput(id: String): Int? {
    return id.toIntOrNull()
  }

  companion object {
    private const val ERROR_PAGE = "error"
  }
}