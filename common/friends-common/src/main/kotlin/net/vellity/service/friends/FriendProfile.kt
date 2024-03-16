package net.vellity.service.friends

import net.vellity.utils.context.Context
import java.util.*

data class FriendProfile(
  val context: Context,
  val player: UUID,
  val friends: List<Friendship>,
  val sentRequests: List<FriendRequest>,
  val receivedRequests: List<FriendRequest>,
  val blockedPlayer: MutableList<BlocklistEntry>,
  val settings: MutableMap<Setting, SettingState>
) {
  fun getSettingState(setting: Setting): SettingState {
    return settings.getOrDefault(setting, setting.default)
  }
}