package net.vellity.service.friends.settings

import net.vellity.service.friends.Setting
import net.vellity.service.friends.SettingState
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

interface FriendSettingsController {
  @GetMapping("/settings")
  fun getSettings(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID
  ): ResponseEntity<Map<Setting, SettingState>>

  @GetMapping("/settings/check")
  fun checkSetting(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("setting") setting: Setting,
    @RequestParam("target") target: UUID,
    @RequestParam("knowIfFriends") knowIfFriends: Boolean
  ): ResponseEntity<Boolean>

  @PostMapping("/settings")
  fun updateSetting(
    @RequestParam("context") context: Context,
    @RequestParam("player") player: UUID,
    @RequestParam("setting") setting: Setting,
    @RequestParam("value") value: SettingState
  ): ResponseEntity<Unit>
}