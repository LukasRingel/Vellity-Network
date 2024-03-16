package net.vellity.service.economy

import net.vellity.service.economy.user.UserBalance
import net.vellity.utils.context.Context
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface UserBalanceRequests {
  @GET("/balance/currency")
  fun getBalance(
    @Query("player") player: UUID,
    @Query("currency") currencyId: Int
  ): Call<UserBalance>

  @GET("/balance/context")
  fun getAllBalancesInContext(
    @Query("player") player: UUID,
    @Query("context") context: Context
  ): Call<List<UserBalance>>
}