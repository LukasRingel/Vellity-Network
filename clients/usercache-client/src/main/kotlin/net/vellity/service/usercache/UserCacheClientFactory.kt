package net.vellity.service.usercache

object UserCacheClientFactory {
  fun create(configuration: UserCacheClientConfiguration): UserCacheClient {
    return UserCacheClient(configuration)
  }
}