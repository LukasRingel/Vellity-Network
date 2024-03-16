package net.vellity.service.friends.profile

import net.vellity.service.friends.FriendProfile
import net.vellity.service.friends.friendship.FriendshipService
import net.vellity.service.friends.settings.FriendSettingsService
import net.vellity.utils.context.Context
import org.springframework.stereotype.Service
import java.util.*

@Service
class NonCachingFriendProfileService(
  private val friendshipService: FriendshipService,
  private val settingsService: FriendSettingsService
) : FriendProfileService {
  override fun getFriendProfile(context: Context, player: UUID): FriendProfile {
    return FriendProfile(
      context,
      player,
      friendshipService.getFriendships(context, player),
      friendshipService.getRequestsSentByPlayer(context, player),
      friendshipService.getOpenRequests(context, player),
      friendshipService.getBlocklist(context, player).toMutableList(),
      settingsService.getSettings(context, player)
    )
  }
}