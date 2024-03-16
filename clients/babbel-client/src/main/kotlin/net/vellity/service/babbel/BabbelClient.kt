package net.vellity.service.babbel

import net.vellity.utils.httpclient.HttpClientFactory

class BabbelClient(configuration: BabbelClientConfiguration) {
  private val httpClient = HttpClientFactory.create(
    configuration.hostname,
    configuration.authKey
  )

  private val userRequests: UserRequests = httpClient.create(UserRequests::class.java)
  private val translationRequests: TranslationRequests = httpClient.create(TranslationRequests::class.java)

  fun users(): UserRequests {
    return userRequests
  }

  fun translations(): TranslationRequests {
    return translationRequests
  }
}