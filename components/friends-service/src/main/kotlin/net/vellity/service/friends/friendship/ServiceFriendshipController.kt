package net.vellity.service.friends.friendship

import net.vellity.service.friends.BlocklistEntry
import net.vellity.service.friends.FriendRequest
import net.vellity.service.friends.Friendship
import net.vellity.service.friends.FriendshipState
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
class ServiceFriendshipController(private val service: FriendshipService) : FriendshipController {
  override fun getFriendships(context: Context, player: UUID): ResponseEntity<List<Friendship>> {
    return ResponseEntity.ok(service.getFriendships(context, player))
  }

  override fun getOnlyFriends(context: Context, player: UUID): ResponseEntity<List<Friendship>> {
    return ResponseEntity.ok(service.getOnlyFriends(context, player))
  }

  override fun getFriendshipWith(context: Context, player: UUID, friend: UUID): ResponseEntity<Friendship?> {
    return ResponseEntity.ok(service.getFriendshipWith(context, player, friend))
  }

  override fun deleteFriendshipWith(context: Context, player: UUID, friend: UUID): ResponseEntity<Unit> {
    return ResponseEntity.ok(service.deleteFriendshipWith(context, player, friend))
  }

  override fun getRequested(context: Context, player: UUID): ResponseEntity<List<FriendRequest>> {
    return ResponseEntity.ok(service.getRequestsSentByPlayer(context, player))
  }

  override fun getRequests(context: Context, player: UUID): ResponseEntity<List<FriendRequest>> {
    return ResponseEntity.ok(service.getOpenRequests(context, player))
  }

  override fun createFriendshipRequest(context: Context, player: UUID, friend: UUID): ResponseEntity<FriendRequest?> {
    return ResponseEntity.ok(service.createFriendshipRequest(context, player, friend))
  }

  override fun acceptFriendshipRequest(context: Context, player: UUID, friend: UUID): ResponseEntity<Friendship?> {
    return ResponseEntity.ok(service.acceptFriendshipRequest(context, player, friend))
  }

  override fun denyFriendshipRequest(context: Context, player: UUID, friend: UUID): ResponseEntity<Unit> {
    service.denyFriendshipRequest(context, player, friend)
    return ResponseEntity.ok().build()
  }

  override fun deleteFriendshipRequest(context: Context, player: UUID, friend: UUID): ResponseEntity<Unit> {
    service.deleteFriendRequest(context, player, friend)
    return ResponseEntity.ok().build()
  }

  override fun updateFriendshipState(
    context: Context,
    player: UUID,
    friend: UUID,
    state: FriendshipState
  ): ResponseEntity<Unit> {
    service.updateFriendshipState(context, player, friend, state)
    return ResponseEntity.ok().build()
  }

  override fun getBlocklist(context: Context, player: UUID): ResponseEntity<List<BlocklistEntry>> {
    return ResponseEntity.ok(service.getBlocklist(context, player))
  }

  override fun addToBlocklist(
    context: Context,
    player: UUID,
    target: UUID,
    activeUntil: Long
  ): ResponseEntity<BlocklistEntry> {
    return ResponseEntity.ok(service.createBlocklistEntry(context, player, target, Instant.ofEpochMilli(activeUntil)))
  }

  override fun removeFromBlocklist(context: Context, player: UUID, target: UUID): ResponseEntity<Unit> {
    service.deleteBlocklistEntry(context, player, target)
    return ResponseEntity.ok().build()
  }
}