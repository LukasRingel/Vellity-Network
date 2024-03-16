package net.vellity.service.economy.currency

import net.vellity.utils.context.Context
import java.time.Instant

data class Currency(
  val id: Int,
  val context: Context,
  val name: String,
  val defaultBalance: Double,
  var booster: List<CurrencyBooster>
) {
  fun booster(booster: List<CurrencyBooster>) {
    this.booster = booster
  }

  fun activeBoosters(): List<CurrencyBooster> {
    return booster.filter { it.startAt.isBefore(Instant.now()) && it.activeUntil.isAfter(Instant.now()) }
  }
}
