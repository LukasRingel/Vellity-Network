package net.vellity.service.friends

enum class SettingState {
  UNKNOWN,
  EVERYBODY,
  FRIENDS,
  FAVORITES,
  NOBODY;

  companion object {
    fun valueOf(id: Int): SettingState {
      for (setting in SettingState.values()) {
        if (setting.ordinal == id) {
          return setting
        }
      }
      throw IllegalArgumentException("Unknown setting state id: $id")
    }
  }
}