package net.vellity.service.economy.transaction

import java.time.Instant
import java.util.UUID

data class Transaction(
  val id: Int,
  val playerId: UUID,
  val currency: Int,
  val amount: Double,
  val action: Action,
  val createdAt: Instant
)