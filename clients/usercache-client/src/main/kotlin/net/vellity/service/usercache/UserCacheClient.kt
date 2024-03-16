package net.vellity.service.usercache

import net.vellity.utils.httpclient.HttpClientFactory

class UserCacheClient(configuration: UserCacheClientConfiguration) {
  private val httpClient = HttpClientFactory.create(
    configuration.hostname,
    configuration.authKey
  )

  private val loginRequests: LoginRequests = httpClient.create(LoginRequests::class.java)
  private val namesRequests: NamesRequests = httpClient.create(NamesRequests::class.java)
  private val texturesRequests: TexturesRequests = httpClient.create(TexturesRequests::class.java)
  private val messagesRequests: MessagesRequests = httpClient.create(MessagesRequests::class.java)
  private val sessionRequests: SessionRequests = httpClient.create(SessionRequests::class.java)
  private val combined: CombinedRequests = httpClient.create(CombinedRequests::class.java)
  private val notifications: NotificationsRequests = httpClient.create(NotificationsRequests::class.java)

  fun logins(): LoginRequests {
    return loginRequests
  }

  fun names(): NamesRequests {
    return namesRequests
  }

  fun textures(): TexturesRequests {
    return texturesRequests
  }

  fun messages(): MessagesRequests {
    return messagesRequests
  }

  fun sessions(): SessionRequests {
    return sessionRequests
  }

  fun combined(): CombinedRequests {
    return combined
  }

  fun notifications(): NotificationsRequests {
    return notifications
  }
}