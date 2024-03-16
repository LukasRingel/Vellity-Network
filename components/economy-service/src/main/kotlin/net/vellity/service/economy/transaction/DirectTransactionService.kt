package net.vellity.service.economy.transaction

import net.vellity.service.economy.currency.CurrencyService
import net.vellity.service.economy.transaction.blueprint.TransactionCreateBlueprint
import net.vellity.service.economy.user.UserBalanceService
import org.springframework.stereotype.Service
import java.util.*

@Service
class DirectTransactionService(
  private val transactionRepository: TransactionRepository,
  private val balanceService: UserBalanceService,
  private val currencyService: CurrencyService
) : TransactionService {
  override fun createTransaction(blueprint: TransactionCreateBlueprint): Transaction {
    currencyService.getCurrencyById(blueprint.currencyId).let {

      var amount = blueprint.amount
      for (booster in it.activeBoosters()) {
        amount *= booster.amount
      }

      balanceService.updateBalance(blueprint.playerId, blueprint.currencyId, amount)
      return transactionRepository.createTransaction(
        blueprint.playerId,
        amount,
        blueprint.currencyId,
        blueprint.action
      )

    }
  }

  override fun revokeTransaction(transactionId: Int) {
    transactionRepository.getTransaction(transactionId)?.let {
      transactionRepository.revokeTransaction(transactionId)
      transactionRepository.createTransaction(it.playerId, -it.amount, it.currency, Action.REVOKE)
      balanceService.updateBalance(it.playerId, it.currency, -it.amount)
    }
  }

  override fun getLastTransactions(playerId: UUID, currencyId: Int, limit: Int): List<Transaction> {
    return transactionRepository.getLastTransactions(playerId, currencyId, limit)
  }
}