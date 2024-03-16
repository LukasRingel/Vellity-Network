package net.vellity.service.economy

import net.vellity.service.economy.transaction.Transaction
import net.vellity.service.economy.transaction.blueprint.TransactionCreateBlueprint
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.*

interface TransactionRequests {
  @POST("/transactions")
  fun createTransaction(
    @Body blueprint: TransactionCreateBlueprint
  ): Call<Transaction>

  @DELETE("/transactions")
  fun revokeTransaction(
    @Query("transaction") transactionId: Int
  ): Call<Unit>

  @GET("/transactions")
  fun getLastTransactions(
    @Query("player") playerId: UUID,
    @Query("currency") currencyId: Int,
    @Query("limit") limit: Int = 25
  ): Call<List<Transaction>>
}