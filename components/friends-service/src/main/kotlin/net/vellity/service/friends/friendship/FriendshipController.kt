package net.vellity.service.friends.friendship

import net.vellity.service.friends.BlocklistEntry
import net.vellity.service.friends.FriendRequest
import net.vellity.service.friends.Friendship
import net.vellity.service.friends.FriendshipState
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

interface FriendshipController {
  @GetMapping("/friendships")
  fun getFriendships(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID
  ): ResponseEntity<List<Friendship>>

  @GetMapping("/friendships/friends")
  fun getOnlyFriends(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID
  ): ResponseEntity<List<Friendship>>

  @GetMapping("/friendships/with")
  fun getFriendshipWith(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("friend") friend: UUID
  ): ResponseEntity<Friendship?>

  @DeleteMapping("/friendships/with")
  fun deleteFriendshipWith(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("friend") friend: UUID
  ): ResponseEntity<Unit>

  @GetMapping("/friendships/requested")
  fun getRequested(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID
  ): ResponseEntity<List<FriendRequest>>

  @GetMapping("/friendships/requests")
  fun getRequests(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID
  ): ResponseEntity<List<FriendRequest>>

  @PostMapping("/friendships/requests")
  fun createFriendshipRequest(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("friend") friend: UUID
  ): ResponseEntity<FriendRequest?>

  @PostMapping("/friendships/requests/accept")
  fun acceptFriendshipRequest(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("friend") friend: UUID
  ): ResponseEntity<Friendship?>

  @PostMapping("/friendships/requests/deny")
  fun denyFriendshipRequest(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("friend") friend: UUID
  ): ResponseEntity<Unit>

  @DeleteMapping("/friendships/requests")
  fun deleteFriendshipRequest(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("friend") friend: UUID
  ): ResponseEntity<Unit>

  @PostMapping("/friendships/state")
  fun updateFriendshipState(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("friend") friend: UUID,
    @RequestParam("state") state: FriendshipState
  ): ResponseEntity<Unit>

  @GetMapping("/friendships/blocklist")
  fun getBlocklist(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID
  ): ResponseEntity<List<BlocklistEntry>>

  @PostMapping("/friendships/blocklist")
  fun addToBlocklist(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("target") target: UUID,
    @RequestParam("activeUntil") activeUntil: Long
  ): ResponseEntity<BlocklistEntry>

  @DeleteMapping("/friendships/blocklist")
  fun removeFromBlocklist(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("target") target: UUID
  ): ResponseEntity<Unit>
}