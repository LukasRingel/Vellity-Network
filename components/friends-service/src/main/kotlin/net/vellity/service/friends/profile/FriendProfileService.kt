package net.vellity.service.friends.profile

import net.vellity.service.friends.FriendProfile
import net.vellity.utils.context.Context
import java.util.*

interface FriendProfileService {
  fun getFriendProfile(context: Context, player: UUID): FriendProfile
}