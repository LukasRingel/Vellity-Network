package net.vellity.service.punish.punishments

enum class PunishmentProofType {
  UNKNOWN,
  CHAT_LOG,
  SCREENSHOT,
  REPLAY;

  companion object {
    fun byId(id: Int): PunishmentProofType {
      return when (id) {
        1 -> CHAT_LOG
        2 -> SCREENSHOT
        3 -> REPLAY
        else -> UNKNOWN
      }
    }
  }
}