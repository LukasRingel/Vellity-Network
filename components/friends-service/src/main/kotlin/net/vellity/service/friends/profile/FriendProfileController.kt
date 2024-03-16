package net.vellity.service.friends.profile

import net.vellity.service.friends.FriendProfile
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

interface FriendProfileController {
  @GetMapping("/profile")
  fun getFriendProfile(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID
  ): ResponseEntity<FriendProfile>
}