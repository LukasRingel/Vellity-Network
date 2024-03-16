package net.vellity.service.economy.currency.blueprint

import net.vellity.utils.context.Context

data class CurrencyCreateBlueprint(
  val context: Context,
  val name: String,
  val defaultValue: Double
)