package net.vellity.service.economy.currency

import java.time.Instant

data class CurrencyBooster(
  val id: Int,
  val currencyId: Int,
  val amount: Double,
  val startAt: Instant,
  val activeUntil: Instant
)