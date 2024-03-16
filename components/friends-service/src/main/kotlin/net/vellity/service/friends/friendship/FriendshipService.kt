package net.vellity.service.friends.friendship

import net.vellity.service.friends.BlocklistEntry
import net.vellity.service.friends.FriendRequest
import net.vellity.service.friends.Friendship
import net.vellity.service.friends.FriendshipState
import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

interface FriendshipService {
  fun getFriendships(context: Context, player: UUID): List<Friendship>

  fun getOpenRequests(context: Context, player: UUID): List<FriendRequest>

  fun getRequestsSentByPlayer(context: Context, player: UUID): List<FriendRequest>

  fun getOnlyFriends(context: Context, player: UUID): List<Friendship>

  fun getFriendshipWith(context: Context, player: UUID, friend: UUID): Friendship?

  fun deleteFriendshipWith(context: Context, player: UUID, friend: UUID)

  fun createFriendshipRequest(context: Context, player: UUID, friend: UUID): FriendRequest

  fun acceptFriendshipRequest(context: Context, player: UUID, friend: UUID): Friendship?

  fun denyFriendshipRequest(context: Context, player: UUID, friend: UUID)

  fun deleteFriendRequest(context: Context, player: UUID, friend: UUID)

  fun updateFriendshipState(context: Context, player: UUID, friend: UUID, state: FriendshipState)

  fun getBlocklist(context: Context, player: UUID): List<BlocklistEntry>

  fun createBlocklistEntry(context: Context, player: UUID, target: UUID, activeUntil: Instant): BlocklistEntry

  fun deleteBlocklistEntry(context: Context, player: UUID, target: UUID)
}