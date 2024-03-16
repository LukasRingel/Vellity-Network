package net.vellity.service.friends

enum class Setting(val default: SettingState) {

  REQUESTS(SettingState.EVERYBODY),
  PRIVATE_MESSAGES(SettingState.FRIENDS),
  NOTIFICATIONS(SettingState.FRIENDS),
  FRIENDS_CAN_JUMP(SettingState.FRIENDS);

  companion object {
    fun valueOf(id: Int): Setting {
      for (setting in values()) {
        if (setting.ordinal == id) {
          return setting
        }
      }
      throw IllegalArgumentException("Unknown setting id: $id")
    }

    fun fillWithRemainingDefaults(map: MutableMap<Setting, SettingState>) {
      for (setting in values()) {
        map.putIfAbsent(setting, setting.default)
      }
    }
  }
}