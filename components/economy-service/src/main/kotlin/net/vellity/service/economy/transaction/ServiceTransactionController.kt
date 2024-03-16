package net.vellity.service.economy.transaction

import net.vellity.service.economy.transaction.blueprint.TransactionCreateBlueprint
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ServiceTransactionController(private val transactionService: TransactionService) : TransactionController {
  override fun createTransaction(blueprint: TransactionCreateBlueprint): ResponseEntity<Transaction> {
    return ResponseEntity.ok(transactionService.createTransaction(blueprint))
  }

  override fun revokeTransaction(transactionId: Int): ResponseEntity<Unit> {
    transactionService.revokeTransaction(transactionId)
    return ResponseEntity.ok().build()
  }

  override fun getLastTransactions(playerId: UUID, currencyId: Int, limit: Int): ResponseEntity<List<Transaction>> {
    return ResponseEntity.ok(transactionService.getLastTransactions(playerId, currencyId, limit))
  }
}