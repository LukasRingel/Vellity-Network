package net.vellity.service.economy

import net.vellity.utils.httpclient.HttpClientFactory

class EconomyClient(configuration: EconomyClientConfiguration) {
  private val httpClient = HttpClientFactory.create(
    configuration.hostname,
    configuration.authKey
  )

  private val currencyRequests: CurrencyRequests = httpClient.create(CurrencyRequests::class.java)
  private val transactionRequests: TransactionRequests = httpClient.create(TransactionRequests::class.java)
  private val balanceRequests: UserBalanceRequests = httpClient.create(UserBalanceRequests::class.java)

  fun currencies(): CurrencyRequests {
    return currencyRequests
  }

  fun transactions(): TransactionRequests {
    return transactionRequests
  }

  fun balances(): UserBalanceRequests {
    return balanceRequests
  }
}