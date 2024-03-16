package net.vellity.service.economy

import net.vellity.service.economy.currency.Currency
import net.vellity.service.economy.currency.CurrencyBooster
import net.vellity.service.economy.currency.blueprint.CurrencyBoosterCreateBlueprint
import net.vellity.service.economy.currency.blueprint.CurrencyCreateBlueprint
import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRequests {
  @GET("/currencies")
  fun getCurrencies(): Call<List<Currency>>

  @GET("/currencies/id")
  fun getCurrencyById(@Query("id") id: Int): Call<List<Currency>>

  @GET("/currencies/context")
  fun getCurrenciesForContext(@Query("context") context: Context): Call<List<Currency>>

  @GET("/currencies")
  fun createCurrency(@Body blueprint: CurrencyCreateBlueprint): Call<Currency>

  @GET("/currencies/booster")
  fun createBooster(@Body blueprint: CurrencyBoosterCreateBlueprint): Call<CurrencyBooster>
}