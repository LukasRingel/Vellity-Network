package net.vellity.service.economy.user

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

interface UserBalanceController {
  @GetMapping("/balance/currency")
  fun getBalance(
    @RequestParam("player") player: UUID,
    @RequestParam("currency") currencyId: Int
  ): ResponseEntity<UserBalance>

  @GetMapping("/balance/context")
  fun getAllBalancesInContext(
    @RequestParam("player") player: UUID,
    @RequestParam("context") context: Context
  ): ResponseEntity<List<UserBalance>>
}