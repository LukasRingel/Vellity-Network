package net.vellity.service.economy.transaction

import net.vellity.service.economy.transaction.blueprint.TransactionCreateBlueprint
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

interface TransactionController {
  @PostMapping("/transactions")
  fun createTransaction(@RequestBody blueprint: TransactionCreateBlueprint): ResponseEntity<Transaction>

  @DeleteMapping("/transactions")
  fun revokeTransaction(@RequestParam("transaction") transactionId: Int): ResponseEntity<Unit>

  @GetMapping("/transactions")
  fun getLastTransactions(
    @RequestParam("player") playerId: UUID,
    @RequestParam("currency") currencyId: Int,
    @RequestParam("limit") limit: Int = 25
  ): ResponseEntity<List<Transaction>>
}