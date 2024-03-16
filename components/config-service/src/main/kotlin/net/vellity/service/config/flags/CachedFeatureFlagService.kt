package net.vellity.service.config.flags

import net.vellity.service.config.FeatureFlag
import net.vellity.service.config.UnknownFlagException
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.nowUtc
import net.vellity.utils.redis.RedisConnection
import net.vellity.utils.redis.RedisSynchronizer
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class CachedFeatureFlagService(
  private val repository: FeatureFlagRepository,
  redisConnection: RedisConnection
) : FeatureFlagsService {

  private val synchronizer = RedisSynchronizer(redisConnection, "config:feature-flags") {
    updateLocalState()
  }

  private var flags: List<FeatureFlag> = repository.getFlags()

  private fun updateLocalState() {
    flags = repository.getFlags()
  }

  override fun getFlags(context: Context): List<FeatureFlag> {
    return flags.filter { it.context == context }
  }

  override fun getFlag(context: Context, name: String): FeatureFlag? {
    return flags.firstOrNull { it.context == context && it.name == name }
  }

  override fun isFlagActive(context: Context, name: String, default: Boolean): Boolean {
    getFlag(context, name)?.let { flag ->
      return flag.enabled && flag.activeUntil.isAfter(nowUtc())
    }
    return default
  }

  override fun createFlag(context: Context, name: String, enabled: Boolean, activeUntil: Instant): FeatureFlag {
    repository.createFlag(context, name, enabled, activeUntil)
      .let { flag ->
        synchronizer.deployUpdate()
        return flag
      }
  }

  override fun updateFlag(context: Context, name: String, enabled: Boolean, activeUntil: Instant): FeatureFlag {
    val featureFlag = getFlag(context, name) ?: throw UnknownFlagException()
    repository.updateFlag(featureFlag.id, enabled, activeUntil)
    synchronizer.deployUpdate()
    return featureFlag.copy(enabled = enabled, activeUntil = activeUntil)
  }
}