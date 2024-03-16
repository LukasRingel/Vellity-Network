package net.vellity.service.economy.transaction.blueprint

import net.vellity.service.economy.transaction.Action
import java.util.*

data class TransactionCreateBlueprint(
  val playerId: UUID,
  val currencyId: Int,
  val action: Action,
  val amount: Double
)