package net.vellity.service.economy.currency

import net.vellity.service.economy.currency.blueprint.CurrencyBoosterCreateBlueprint
import net.vellity.service.economy.currency.blueprint.CurrencyCreateBlueprint
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

interface CurrencyController {
  @GetMapping("/currencies")
  fun getCurrencyById(): ResponseEntity<List<Currency>>

  @GetMapping("/currencies/id")
  fun getCurrencyById(@RequestParam("id") id: Int): ResponseEntity<List<Currency>>

  @GetMapping("/currencies/context")
  fun getCurrenciesForContext(@RequestParam("context") context: Context): ResponseEntity<List<Currency>>

  @PostMapping("/currencies")
  fun createCurrency(@RequestBody blueprint: CurrencyCreateBlueprint): ResponseEntity<Currency>

  @PostMapping("/currencies/booster")
  fun createBooster(@RequestBody blueprint: CurrencyBoosterCreateBlueprint): ResponseEntity<CurrencyBooster>
}