package net.vellity.service.economy.user

import java.util.*

data class UserBalance(
  val id: Int,
  val playerId: UUID,
  val balance: Double,
  val currency: Int
)