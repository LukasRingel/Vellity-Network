package net.vellity.service.friends.settings

import net.vellity.service.friends.Setting
import net.vellity.service.friends.SettingState
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class ServiceFriendSettingsController(private val service: FriendSettingsService) : FriendSettingsController {
  override fun getSettings(context: Context, player: UUID): ResponseEntity<Map<Setting, SettingState>> {
    return ResponseEntity.ok(service.getSettings(context, player))
  }

  override fun checkSetting(context: Context, player: UUID, setting: Setting, target: UUID, knowIfFriends: Boolean): ResponseEntity<Boolean> {
    return ResponseEntity.ok(service.checkSetting(context, player, setting, target, knowIfFriends))
  }

  override fun updateSetting(context: Context, player: UUID, setting: Setting, value: SettingState): ResponseEntity<Unit> {
    service.updateSetting(context, player, setting, value)
    return ResponseEntity.ok().build()
  }
}