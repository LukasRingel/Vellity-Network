package net.vellity.service.usercache.session.redis

import net.vellity.service.usercache.session.UserSession
import java.util.*

class UserSessionMapper {
  companion object {
    fun mapToUserSession(map: Map<String, String>): UserSession {
      return UserSession(
        UUID.fromString(map.get("player")),
        map.getOrDefault("login", "1").toInt(),
        map.getOrDefault("currentServer", "?"),
        map.getOrDefault("previousServer", "?"),
        map.getOrDefault("currentProxy", "?")
      )
    }

    fun userSessionToMap(userSession: UserSession): Map<String, String> {
      return mapOf(
        "player" to userSession.player.toString(),
        "login" to userSession.login.toString(),
        "currentServer" to userSession.currentServer,
        "previousServer" to userSession.previousServer,
        "currentProxy" to userSession.currentProxy
      )
    }
  }
}