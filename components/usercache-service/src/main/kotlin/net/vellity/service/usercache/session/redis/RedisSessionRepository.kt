package net.vellity.service.usercache.session.redis

import net.vellity.service.usercache.login.PlayerLogin
import net.vellity.service.usercache.session.UserSession
import net.vellity.service.usercache.session.UserSessionRepository
import net.vellity.utils.context.Context
import net.vellity.utils.redis.RedisConnection
import org.springframework.stereotype.Component
import java.util.*

@Component
class RedisSessionRepository(private val redisConnection: RedisConnection) : UserSessionRepository {
  override fun getCurrentSession(context: Context, player: UUID): UserSession {
    return UserSessionMapper.mapToUserSession(redisConnection.syncCommands().hgetall(generateKey(context, player)))
  }

  override fun getCurrentSessionsOnContext(context: Context): List<UserSession> {
    return redisConnection.syncCommands().keys(generateTemplateKey(context)).map {
      UserSessionMapper.mapToUserSession(redisConnection.syncCommands().hgetall(it))
    }
  }

  override fun updateCurrentServer(context: Context, player: UUID, server: String) {
    getCurrentSession(context, player).let {
      redisConnection.syncCommands().hset(generateKey(context, player), "previousServer", it.currentServer)
      redisConnection.syncCommands().hset(generateKey(context, player), "currentServer", server)
    }
  }

  override fun updateCurrentProxy(context: Context, player: UUID, proxy: String) {
    redisConnection.syncCommands().hset(generateKey(context, player), "currentProxy", proxy)
  }

  override fun destroySession(context: Context, player: UUID) {
    redisConnection.syncCommands().del(generateKey(context, player))
  }

  override fun createSession(context: Context, player: UUID, login: PlayerLogin) {
    redisConnection.syncCommands().hmset(
      generateKey(context, player),
      UserSessionMapper.userSessionToMap(UserSession.empty(player, login.id))
    )
    updateTtl(context, player)
  }

  override fun keepSessionsAlive(context: Context, players: List<UUID>) {
    players.forEach {
      updateTtl(context, it)
    }
  }

  private fun updateTtl(context: Context, player: UUID) {
    redisConnection.syncCommands().expire(generateKey(context, player), 10)
  }

  companion object {
    private fun generateKey(context: Context, player: UUID): String =
      "network:sessions:${context.name.lowercase()}:${player}"

    private fun generateTemplateKey(context: Context): String =
      "network:sessions:${context.name.lowercase()}:*"
  }
}