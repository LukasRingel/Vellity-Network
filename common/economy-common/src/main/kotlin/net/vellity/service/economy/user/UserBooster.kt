package net.vellity.service.economy.user

import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

data class UserBooster(
  val id: Int,
  val playerId: UUID,
  val currencyId: Int,
  val amount: Double,
  val activeUntil: Instant
)