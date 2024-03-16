package net.vellity.service.friends.settings

import net.vellity.service.friends.Setting
import net.vellity.service.friends.SettingState
import net.vellity.utils.context.Context
import java.util.*

interface FriendSettingsService {
  fun getSettings(context: Context, player: UUID): MutableMap<Setting, SettingState>

  fun checkSetting(context: Context, player: UUID, setting: Setting, target: UUID, knowIfFriends: Boolean): Boolean

  fun updateSetting(context: Context, player: UUID, setting: Setting, value: SettingState)
}