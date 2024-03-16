package net.vellity.minecraft.common

import net.vellity.service.access.AccessClient
import net.vellity.service.access.AccessClientConfiguration
import net.vellity.service.access.AccessClientFactory
import net.vellity.service.babbel.BabbelClient
import net.vellity.service.babbel.BabbelClientConfiguration
import net.vellity.service.babbel.BabbelClientFactory
import net.vellity.service.config.ConfigClient
import net.vellity.service.config.ConfigClientConfiguration
import net.vellity.service.config.ConfigClientFactory
import net.vellity.service.economy.EconomyClient
import net.vellity.service.economy.EconomyClientConfiguration
import net.vellity.service.economy.EconomyClientFactory
import net.vellity.service.explorer.ExplorerClient
import net.vellity.service.explorer.ExplorerClientConfiguration
import net.vellity.service.explorer.ExplorerClientFactory
import net.vellity.service.friends.FriendsClient
import net.vellity.service.friends.FriendsClientConfiguration
import net.vellity.service.friends.FriendsClientFactory
import net.vellity.service.guardian.GuardianClient
import net.vellity.service.guardian.GuardianClientConfiguration
import net.vellity.service.guardian.GuardianClientFactory
import net.vellity.service.punish.PunishClient
import net.vellity.service.punish.PunishClientConfiguration
import net.vellity.service.punish.PunishClientFactory
import net.vellity.service.usercache.UserCacheClient
import net.vellity.service.usercache.UserCacheClientConfiguration
import net.vellity.service.usercache.UserCacheClientFactory
import net.vellity.utils.configuration.Environment
import net.vellity.utils.redis.RedisConnection
import net.vellity.utils.redis.RedisConnectionConfiguration
import net.vellity.utils.redis.RedisConnectionFactory

val accessClient: AccessClient = AccessClientFactory.create(
  AccessClientConfiguration(
    Environment.getOrDefault("ACCESS_HOST_URL", "https://api.vellity.net/access/"),
    Environment.getOrDefault("ACCESS_API_KEY", "1234567890"),
  )
)

val babbelClient: BabbelClient = BabbelClientFactory.create(
  BabbelClientConfiguration(
    Environment.getOrDefault("BABBEL_HOST_URL", "https://api.vellity.net/babbel/"),
    Environment.getOrDefault("BABBEL_API_KEY", "1234567890"),
  )
)

val configClient: ConfigClient = ConfigClientFactory.create(
  ConfigClientConfiguration(
    Environment.getOrDefault("CONFIG_HOST_URL", "https://api.vellity.net/configuration/"),
    Environment.getOrDefault("CONFIG_API_KEY", "1234567890"),
  )
)

val economyClient: EconomyClient = EconomyClientFactory.create(
  EconomyClientConfiguration(
    Environment.getOrDefault("ECONOMY_HOST_URL", "https://api.vellity.net/economy/"),
    Environment.getOrDefault("ECONOMY_API_KEY", "1234567890"),
  )
)

val explorerClient: ExplorerClient = ExplorerClientFactory.create(
  ExplorerClientConfiguration(
    Environment.getOrDefault("EXPLORER_HOST_URL", "https://api.vellity.net/explorer/"),
    Environment.getOrDefault("EXPLORER_API_KEY", "1234567890"),
  )
)

val friendsClient: FriendsClient = FriendsClientFactory.create(
  FriendsClientConfiguration(
    Environment.getOrDefault("FRIENDS_HOST_URL", "https://api.vellity.net/friends/"),
    Environment.getOrDefault("FRIENDS_API_KEY", "1234567890"),
  )
)

val guardianClient: GuardianClient = GuardianClientFactory.create(
  GuardianClientConfiguration(
    Environment.getOrDefault("GUARDIAN_HOST_URL", "https://api.vellity.net/guardian/"),
    Environment.getOrDefault("GUARDIAN_API_KEY", "1234567890"),
  )
)

val usercacheClient: UserCacheClient = UserCacheClientFactory.create(
  UserCacheClientConfiguration(
    Environment.getOrDefault("USERCACHE_HOST_URL", "https://api.vellity.net/usercache/"),
    Environment.getOrDefault("USERCACHE_API_KEY", "1234567890"),
  )
)

val punishClient: PunishClient = PunishClientFactory.create(
  PunishClientConfiguration(
    Environment.getOrDefault("PUNISH_HOST_URL", "https://api.vellity.net/punish/"),
    Environment.getOrDefault("PUNISH_API_KEY", "1234567890"),
  )
)

val redisClient: RedisConnection = RedisConnectionFactory.create(
  RedisConnectionConfiguration(
    Environment.getOrDefault("REDIS_HOST_URL", "database.vellity.net"),
    Environment.getOrDefault("REDIS_HOST_PORT", "6379").toInt(),
    Environment.getOrDefault("REDIS_USER_PASSWORD", "1234567890"),
  )
)