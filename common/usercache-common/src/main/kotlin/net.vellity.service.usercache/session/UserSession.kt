package net.vellity.service.usercache.session

import java.util.UUID

data class UserSession(
  val player: UUID,
  val login: Int,
  val currentServer: String,
  val previousServer: String,
  val currentProxy: String
) {
  companion object {
    fun empty(player: UUID, login: Int): UserSession {
      return UserSession(player, login, "?", "?", "?")
    }
  }
}