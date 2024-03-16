package net.vellity.service.economy.user

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ServiceUserBalanceController(private val userBalanceService: UserBalanceService) : UserBalanceController {
  override fun getBalance(player: UUID, currencyId: Int): ResponseEntity<UserBalance> {
    return ResponseEntity.ok(userBalanceService.getBalance(player, currencyId))
  }

  override fun getAllBalancesInContext(player: UUID, context: Context): ResponseEntity<List<UserBalance>> {
    return ResponseEntity.ok(userBalanceService.getAllBalancesInContext(player, context))
  }
}