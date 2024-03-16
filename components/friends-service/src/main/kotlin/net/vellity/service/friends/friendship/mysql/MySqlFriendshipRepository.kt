package net.vellity.service.friends.friendship.mysql

import net.vellity.service.friends.BlocklistEntry
import net.vellity.service.friends.FriendRequest
import net.vellity.service.friends.Friendship
import net.vellity.service.friends.FriendshipState
import net.vellity.service.friends.exceptions.PlayerNotBlockedException
import net.vellity.service.friends.friendship.FriendshipRepository
import net.vellity.service.friends.friendship.mysql.blocklist.InsertBlocklistEntry
import net.vellity.service.friends.friendship.mysql.blocklist.SelectBlocklistEntry
import net.vellity.service.friends.friendship.mysql.blocklist.SelectBlocklistForPlayerInContext
import net.vellity.service.friends.friendship.mysql.blocklist.UpdateActiveUntilForBlocklistEntry
import net.vellity.service.friends.friendship.mysql.friends.InsertFriendship
import net.vellity.service.friends.friendship.mysql.friends.SelectFriendshipWithPlayer
import net.vellity.service.friends.friendship.mysql.friends.SelectFriendships
import net.vellity.service.friends.friendship.mysql.requests.InsertFriendRequest
import net.vellity.service.friends.friendship.mysql.requests.SelectRequestsForPlayer
import net.vellity.service.friends.friendship.mysql.requests.SelectRequestsSentByPlayer
import net.vellity.service.friends.friendship.mysql.requests.UpdateActiveUntilForRequest
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.extensions.nowUtc
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class MySqlFriendshipRepository(private val mySqlConnection: MySqlConnection) : FriendshipRepository {
  override fun getFriendships(context: Context, player: UUID, limit: Int): List<Friendship> {
    return mySqlConnection.executeQuery(SelectFriendships(context, player, limit))
  }

  override fun getFriendshipWith(context: Context, player: UUID, friend: UUID): Friendship? {
    return mySqlConnection.executeQuery(SelectFriendshipWithPlayer(context, player, friend)).firstOrNull()
  }

  override fun createFriendship(context: Context, player: UUID, friend: UUID): Friendship {
    mySqlConnection.executeUpdateAndGetId(InsertFriendship(context, player, friend)).let {
      return Friendship(
        it,
        player,
        friend,
        FriendshipState.NORMAL,
        nowUtc(),
        nowUtc()
      )
    }
  }

  override fun updateFriendshipActiveUntil(context: Context, playerOne: UUID, playerTwo: UUID, activeUntil: Instant) {
    mySqlConnection.executeUpdate(UpdateActiveUntilOfFriendship(context, playerOne, playerTwo, activeUntil))
    mySqlConnection.executeUpdate(UpdateActiveUntilOfFriendship(context, playerTwo, playerOne, activeUntil))
  }

  override fun getRequestsSentByPlayer(context: Context, player: UUID): List<FriendRequest> {
    return mySqlConnection.executeQuery(SelectRequestsSentByPlayer(context, player))
  }

  override fun getOpenRequestsForPlayer(context: Context, player: UUID): List<FriendRequest> {
    return mySqlConnection.executeQuery(SelectRequestsForPlayer(context, player))
  }

  override fun createFriendRequest(context: Context, player: UUID, friend: UUID, activeUntil: Instant): FriendRequest {
    mySqlConnection.executeUpdateAndGetId(InsertFriendRequest(context, player, friend, activeUntil)).let {
      return FriendRequest(it, context, player, friend, activeUntil, nowUtc())
    }
  }

  override fun updateFriendRequestActiveUntil(friendshipId: Int, activeUntil: Instant) {
    mySqlConnection.executeUpdate(UpdateActiveUntilForRequest(friendshipId, activeUntil))
  }

  override fun updateFriendshipState(friendshipId: Int, state: FriendshipState) {
    mySqlConnection.executeUpdate(UpdateFriendshipState(friendshipId, state))
  }

  override fun getBlocklist(context: Context, player: UUID): List<BlocklistEntry> {
    return mySqlConnection.executeQuery(SelectBlocklistForPlayerInContext(context, player))
  }

  override fun createBlocklistEntry(
    context: Context,
    player: UUID,
    target: UUID,
    activeUntil: Instant
  ): BlocklistEntry {
    return mySqlConnection.executeUpdateAndGetId(
      InsertBlocklistEntry(context, player, target, activeUntil)
    ).let {
      BlocklistEntry(
        it,
        context,
        player,
        target,
        activeUntil,
        nowUtc()
      )
    }
  }

  override fun deleteBlocklistEntry(context: Context, player: UUID, target: UUID) {
    val blocklistEntry = mySqlConnection.executeQuery(SelectBlocklistEntry(context, player, target)).firstOrNull()
      ?: throw PlayerNotBlockedException()
    mySqlConnection.executeUpdate(UpdateActiveUntilForBlocklistEntry(blocklistEntry.id, nowUtc()))
  }
}