package net.vellity.service.punish.reason

enum class PunishmentTimeUnit {
  MINUTES,
  HOURS,
  DAYS,
  MONTHS,
  PERMANENT;

  companion object {
    fun byId(id: Int): PunishmentTimeUnit {
      return when (id) {
        1 -> MINUTES
        2 -> HOURS
        3 -> DAYS
        4 -> MONTHS
        5 -> PERMANENT
        else -> throw IllegalArgumentException("Unknown punishment time unit id: $id")
      }
    }
  }
}