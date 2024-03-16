package net.vellity.service.punish.reason

data class ReasonDuration(
  val id: Int,
  val reasonId: Int,
  val defaultAmount: Int,
  val defaultTimeUnit: PunishmentTimeUnit,
  val multiplicationFactor: Double,
)