package net.vellity.service.economy.currency

import net.vellity.service.economy.currency.blueprint.CurrencyBoosterCreateBlueprint
import net.vellity.service.economy.currency.blueprint.CurrencyCreateBlueprint
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceCurrencyController(private val currencyService: CurrencyService) : CurrencyController {
  override fun getCurrencyById(): ResponseEntity<List<Currency>> {
    return ResponseEntity.ok(currencyService.getAllCurrencies())
  }

  override fun getCurrencyById(id: Int): ResponseEntity<List<Currency>> {
    return ResponseEntity.ok(currencyService.getCurrencies(Context.valueOf(id)))
  }

  override fun getCurrenciesForContext(context: Context): ResponseEntity<List<Currency>> {
    return ResponseEntity.ok(currencyService.getCurrencies(context))
  }

  override fun createCurrency(blueprint: CurrencyCreateBlueprint): ResponseEntity<Currency> {
    return ResponseEntity.ok(currencyService.createCurrency(blueprint))
  }

  override fun createBooster(blueprint: CurrencyBoosterCreateBlueprint): ResponseEntity<CurrencyBooster> {
    return ResponseEntity.ok(currencyService.createBooster(blueprint))
  }
}