package net.vellity.service.friends.settings

import net.vellity.service.friends.Setting
import net.vellity.service.friends.SettingState
import net.vellity.utils.context.Context
import java.util.*

interface FriendSettingsRepository {
  fun getSettings(context: Context, player: UUID): List<Pair<Int, Int>>

  fun updateSetting(context: Context, player: UUID, setting: Setting, value: SettingState)
}