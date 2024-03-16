package net.vellity.service.friends

import java.time.Instant
import java.util.*

data class Friendship(
  val id: Int,
  val player: UUID,
  val friend: UUID,
  var state: FriendshipState,
  val createdAt: Instant,
  val changedAt: Instant
)