package net.vellity.service.punish.punishments

enum class PunishmentActionType {
  CREATE,
  PARDON,
  UPDATE_PROOF;

  companion object {
    fun byId(id: Int): PunishmentActionType {
      return when (id) {
        1 -> CREATE
        2 -> PARDON
        3 -> UPDATE_PROOF
        else -> throw IllegalArgumentException("Unknown punishment action type id: $id")
      }
    }
  }
}