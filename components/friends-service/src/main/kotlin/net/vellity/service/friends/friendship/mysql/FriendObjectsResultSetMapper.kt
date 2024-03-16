package net.vellity.service.friends.friendship.mysql

import net.vellity.service.friends.BlocklistEntry
import net.vellity.service.friends.FriendRequest
import net.vellity.service.friends.Friendship
import net.vellity.service.friends.FriendshipState
import net.vellity.utils.mysql.extensions.getContext
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.getUuid
import java.sql.ResultSet
import java.util.function.Function

class FriendObjectsResultSetMapper {
  companion object {
    val resultSetToFriendship: Function<ResultSet, Friendship> = Function {
      Friendship(
        it.getInt("id"),
        it.getUuid("player"),
        it.getUuid("friend"),
        FriendshipState.valueOf(it.getInt("state")),
        it.getInstant("createdAt"),
        it.getInstant("changedAt")
      )
    }

    val resultSetToFriendRequest: Function<ResultSet, FriendRequest> = Function {
      FriendRequest(
        it.getInt("id"),
        it.getContext("context"),
        it.getUuid("player"),
        it.getUuid("target"),
        it.getInstant("activeUntil"),
        it.getInstant("createdAt")
      )
    }

    val resultSetToBlocklistEntry: Function<ResultSet, BlocklistEntry> = Function {
      BlocklistEntry(
        it.getInt("id"),
        it.getContext("context"),
        it.getUuid("player"),
        it.getUuid("target"),
        it.getInstant("activeUntil"),
        it.getInstant("createdAt")
      )
    }
  }
}