package net.vellity.service.punish.punishments

enum class PunishmentType {
  UNKNOWN,
  BAN_CHAT,
  BAN_NETWORK;

  companion object {
    fun byId(id: Int): PunishmentType {
      return when (id) {
        1 -> BAN_CHAT
        2 -> BAN_NETWORK
        else -> UNKNOWN
      }
    }
  }
}