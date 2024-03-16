package net.vellity.service.friends.profile

import net.vellity.service.friends.FriendProfile
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ServiceFriendProfileController(private val service: FriendProfileService) : FriendProfileController {
  override fun getFriendProfile(context: Context, player: UUID): ResponseEntity<FriendProfile> {
    return ResponseEntity.ok(service.getFriendProfile(context, player))
  }
}