package net.vellity.service.friends.settings

import net.vellity.service.friends.FriendshipState
import net.vellity.service.friends.Setting
import net.vellity.service.friends.SettingState
import net.vellity.service.friends.friendship.FriendshipService
import net.vellity.utils.context.Context
import org.springframework.stereotype.Service
import java.util.*

@Service
class NonCachingSettingsService(
  private val settingsRepository: FriendSettingsRepository,
  private val friendService: FriendshipService
) : FriendSettingsService {
  override fun getSettings(context: Context, player: UUID): MutableMap<Setting, SettingState> {
    val updatedSettings = settingsRepository.getSettings(context, player)
    val settings = mutableMapOf<Setting, SettingState>()
    for (updatedSetting in updatedSettings) {
      settings[Setting.valueOf(updatedSetting.first)] = SettingState.valueOf(updatedSetting.second)
    }
    Setting.fillWithRemainingDefaults(settings)
    return settings
  }

  override fun checkSetting(
    context: Context,
    player: UUID,
    setting: Setting,
    target: UUID,
    knowIfFriends: Boolean
  ): Boolean {
    val settings = settingsRepository.getSettings(context, player)
    val state = if (settings.any { it.first == setting.ordinal }) {
      SettingState.valueOf(settings.first { it.first == setting.ordinal }.second)
    } else {
      setting.default
    }

    if (state == SettingState.EVERYBODY) {
      return true
    }

    if (state == SettingState.NOBODY) {
      return false
    }

    if (state == SettingState.FRIENDS && knowIfFriends) {
      return true
    }

    if (state == SettingState.FRIENDS) {
      return friendService.getFriendshipWith(context, player, target) != null
    }

    if (state == SettingState.FAVORITES) {
      val friendship = friendService.getFriendshipWith(context, player, target)
      return friendship != null && friendship.state == FriendshipState.FAVORITE
    }

    return false
  }


  override fun updateSetting(context: Context, player: UUID, setting: Setting, value: SettingState) {
    settingsRepository.updateSetting(context, player, setting, value)
  }
}