package net.vellity.service.economy.currency.blueprint

data class CurrencyBoosterCreateBlueprint(
  val currencyId: Int,
  val amount: Double,
  val startAt: Long,
  val activeUntil: Long
)