package net.vellity.service.friends.friendship

import net.vellity.service.friends.BlocklistEntry
import net.vellity.service.friends.FriendRequest
import net.vellity.service.friends.Friendship
import net.vellity.service.friends.FriendshipState
import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

interface FriendshipRepository {
  fun getFriendships(context: Context, player: UUID, limit: Int): List<Friendship>

  fun getFriendshipWith(context: Context, player: UUID, friend: UUID): Friendship?

  fun createFriendship(context: Context, player: UUID, friend: UUID): Friendship

  fun updateFriendshipActiveUntil(context: Context, playerOne: UUID, playerTwo: UUID, activeUntil: Instant)

  fun getRequestsSentByPlayer(context: Context, player: UUID): List<FriendRequest>

  fun getOpenRequestsForPlayer(context: Context, player: UUID): List<FriendRequest>

  fun createFriendRequest(context: Context, player: UUID, friend: UUID, activeUntil: Instant): FriendRequest

  fun updateFriendRequestActiveUntil(friendshipId: Int, activeUntil: Instant)

  fun updateFriendshipState(friendshipId: Int, state: FriendshipState)

  fun getBlocklist(context: Context, player: UUID): List<BlocklistEntry>

  fun createBlocklistEntry(context: Context, player: UUID, target: UUID, activeUntil: Instant): BlocklistEntry

  fun deleteBlocklistEntry(context: Context, player: UUID, target: UUID)
}