package net.vellity.service.punish.reason

import net.vellity.service.punish.punishments.PunishmentType
import net.vellity.utils.context.Context
import net.vellity.utils.redis.RedisConnection
import net.vellity.utils.redis.RedisSynchronizer
import org.springframework.stereotype.Service

@Service
class CachedReasonService(private val repository: ReasonsRepository, redisConnection: RedisConnection) :
  ReasonsService {

  private val synchronizer = RedisSynchronizer(redisConnection, SYNCHRONIZER_KEY) {
    updateLocalState()
  }

  private var reasons: List<Reason> = repository.getAllReasons()

  private fun updateLocalState() {
    reasons = repository.getAllReasons()
  }

  private fun deployUpdate() {
    synchronizer.deployUpdate()
  }

  override fun getReasons(context: Context): List<Reason> =
    reasons.filter { it.context == context }

  override fun getReasonById(id: Int): Reason? =
    reasons.firstOrNull { it.id == id }

  override fun createReason(context: Context, name: String, type: PunishmentType): Reason =
    repository.createReason(context, name, type).also {
      deployUpdate()
    }

  override fun deleteReason(id: Int) =
    repository.deleteReason(id).also {
      deployUpdate()
    }

  override fun createOrUpdateDuration(
    reasonId: Int,
    amount: Int,
    timeunit: PunishmentTimeUnit,
    multiplier: Double
  ): ReasonDuration {
    val reason = getReasonById(reasonId) ?: throw ReasonNotFoundException()
    if (reason.duration != null) {
      repository.deleteDuration(reason)
    }
    val duration = repository.createDuration(reason, amount, timeunit, multiplier)
    deployUpdate()
    return duration
  }

  companion object {
    private const val SYNCHRONIZER_KEY = "punish:reasons"
  }
}