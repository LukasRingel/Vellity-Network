package net.vellity.service.web.chatlog.view

import net.vellity.utils.configuration.Environment
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.servlet.view.RedirectView

@Controller
class ChatlogSearchController {
  @GetMapping("/")
  fun searchChatlog(model: Model): String {
    model.addAttribute("open", SearchRequest(0))
    return "search"
  }

  @PostMapping("/open")
  fun openLog(@ModelAttribute("open") open: SearchRequest): RedirectView {
    return RedirectView(BASE_URL + "/view/${open.id}")
  }

  data class SearchRequest(var id: Int)

  companion object {
    private val BASE_URL = Environment.getOrDefault("CHATLOG_VIEW_BASE_URL", "https://chatlog.vellity.net")
  }
}