package net.vellity.service.explorer.registry

import net.vellity.service.explorer.Identity
import net.vellity.service.explorer.registry.generator.ServiceIdGenerator
import net.vellity.utils.context.Context
import net.vellity.utils.redis.RedisConnection
import org.springframework.stereotype.Component

@Component
class RedisServiceRegistryRepository(
  private val redisConnection: RedisConnection,
  private val idGenerator: ServiceIdGenerator
) : ServiceRegistryRepository {
  override fun getAll(context: Context): List<Identity> {
    val keys = redisConnection.syncCommands().keys("$REDIS_KEY_PREFIX:${context.name}:*")
    return keys.map {
      val map = redisConnection.syncCommands().hgetall(it)
      IdentityMapper.fromMap(map)
    }
  }

  override fun getAllOfType(type: String, context: Context): List<Identity> {
    val keys = redisConnection.syncCommands().keys("$REDIS_KEY_PREFIX:${context.name}:$type:*")
    return keys.map {
      val map = redisConnection.syncCommands().hgetall(it)
      IdentityMapper.fromMap(map)
    }
  }

  override fun register(type: String, hostname: String, port: Int, context: Context): Identity {
    val id = idGenerator.generateNewId(type)
    val identity = Identity(
      id,
      context,
      type,
      hostname,
      port
    )
    redisConnection.syncCommands().hset(
      buildKey(type, id, context),
      IdentityMapper.toMap(identity)
    )
    updateExpirationOfRedisEntry(type, id, context)
    return identity
  }

  override fun updateHearthBeat(type: String, id: String, context: Context, hostname: String, port: Int) {
    if (!exists(type, id, context)) {
      val identity = Identity(
        id,
        context,
        type,
        hostname,
        port
      )
      redisConnection.syncCommands().hset(
        buildKey(type, id, context),
        IdentityMapper.toMap(identity)
      )
    }
    updateExpirationOfRedisEntry(type, id, context)
  }

  override fun unregister(type: String, id: String, context: Context) {
    redisConnection.syncCommands().del(buildKey(type, id, context))
  }

  private fun exists(type: String, id: String, context: Context): Boolean {
    return redisConnection.syncCommands().exists(buildKey(type, id, context)) != 0L
  }

  private fun updateExpirationOfRedisEntry(type: String, id: String, context: Context) {
    redisConnection.syncCommands().expire(
      buildKey(type, id, context),
      EXPIRATION_TIME_SECONDS
    )
  }

  private fun buildKey(type: String, id: String, context: Context): String {
    return "$REDIS_KEY_PREFIX:${context.name}:$type:$id"
  }

  companion object {
    private const val EXPIRATION_TIME_SECONDS: Long = 30
    private const val REDIS_KEY_PREFIX = "network:service"
  }
}