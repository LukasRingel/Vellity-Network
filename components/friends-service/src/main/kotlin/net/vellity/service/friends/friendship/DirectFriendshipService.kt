package net.vellity.service.friends.friendship

import net.vellity.service.friends.*
import net.vellity.service.friends.exceptions.*
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.nowUtc
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class DirectFriendshipService(
  private val friendshipRepository: FriendshipRepository,
  configuration: FriendsServiceConfiguration
) : FriendshipService {

  private val defaultQueryLimit = configuration.getDefaultFriendshipQueryLimit()
  private val defaultRequestDuration = configuration.getRequestActiveUntilDays()

  override fun getFriendships(context: Context, player: UUID): List<Friendship> {
    return friendshipRepository.getFriendships(context, player, defaultQueryLimit)
  }

  override fun getOpenRequests(context: Context, player: UUID): List<FriendRequest> {
    return friendshipRepository.getOpenRequestsForPlayer(context, player)
  }

  override fun getRequestsSentByPlayer(context: Context, player: UUID): List<FriendRequest> {
    return friendshipRepository.getRequestsSentByPlayer(context, player)
  }

  override fun getOnlyFriends(context: Context, player: UUID): List<Friendship> {
    return friendshipRepository.getFriendships(context, player, defaultQueryLimit)
  }

  override fun getFriendshipWith(context: Context, player: UUID, friend: UUID): Friendship? {
    return friendshipRepository.getFriendshipWith(context, player, friend)
  }

  override fun deleteFriendshipWith(context: Context, player: UUID, friend: UUID) {
    friendshipRepository.updateFriendshipActiveUntil(context, player, friend, nowUtc())
  }

  override fun createFriendshipRequest(context: Context, player: UUID, friend: UUID): FriendRequest {
    if (friendshipRepository.getRequestsSentByPlayer(context, player).firstOrNull { it.target == friend } != null) {
      throw AlreadyRequestedByPlayerException()
    }

    if (friendshipRepository.getOpenRequestsForPlayer(context, player).firstOrNull { it.sender == friend } != null) {
      throw AlreadyRequestedByFriendException()
    }

    if (friendshipRepository.getBlocklist(context, friend).firstOrNull { it.target == player } != null) {
      throw PlayerBlockedException()
    }

    return friendshipRepository.createFriendRequest(
      context,
      player,
      friend,
      nowUtc().plus(defaultRequestDuration, ChronoUnit.DAYS)
    )
  }

  override fun acceptFriendshipRequest(context: Context, player: UUID, friend: UUID): Friendship {
    val request = getOpenRequests(context, player).firstOrNull { it.sender == friend }
      ?: throw NoFriendRequestFoundForPlayers()
    friendshipRepository.updateFriendRequestActiveUntil(request.id, nowUtc())
    friendshipRepository.createFriendship(context, friend, player)
    return friendshipRepository.createFriendship(context, player, friend)
  }

  override fun denyFriendshipRequest(context: Context, player: UUID, friend: UUID) {
    val request = getOpenRequests(context, player).firstOrNull { it.sender == friend }
      ?: throw NoFriendRequestFoundForPlayers()
    friendshipRepository.updateFriendRequestActiveUntil(request.id, nowUtc())
  }

  override fun deleteFriendRequest(context: Context, player: UUID, friend: UUID) {
    var request = getRequestsSentByPlayer(context, player).firstOrNull { it.target == friend }

    if (request == null) {
      request = getOpenRequests(context, player).firstOrNull { it.sender == friend }
    }

    if (request == null) {
      throw NoFriendRequestFoundForPlayers()
    }

    friendshipRepository.updateFriendRequestActiveUntil(request.id, nowUtc())
  }

  override fun updateFriendshipState(context: Context, player: UUID, friend: UUID, state: FriendshipState) {
    val friendship = getFriendshipWith(context, player, friend) ?: throw NoFriendsException()
    friendshipRepository.updateFriendshipState(friendship.id, state)
  }

  override fun getBlocklist(context: Context, player: UUID): List<BlocklistEntry> {
    return friendshipRepository.getBlocklist(context, player)
  }

  override fun createBlocklistEntry(
    context: Context,
    player: UUID,
    target: UUID,
    activeUntil: Instant
  ): BlocklistEntry {
    return friendshipRepository.createBlocklistEntry(context, player, target, activeUntil)
  }

  override fun deleteBlocklistEntry(context: Context, player: UUID, target: UUID) {
    friendshipRepository.deleteBlocklistEntry(context, player, target)
  }
}