package net.vellity.utils.mojang

import net.vellity.utils.mojang.model.ProfileServerResponse
import net.vellity.utils.mojang.model.SessionServerResponse
import java.util.*

object MojangServers {
  private const val PROFILE_URL = "https://api.mojang.com/users/profiles/minecraft/"
  private const val SESSION_URL = "https://sessionserver.mojang.com/session/minecraft/profile/"

  fun uuidFromName(name: String): UUID? {
    val request = requestDataFromProfileServer(name)
    val uuid = StringBuilder()
    for (i in 0..31) {
      uuid.append(request.id[i])
      if (i == 7 || i == 11 || i == 15 || i == 19) {
        uuid.append('-')
      }
    }
    return UUID.fromString(uuid.toString())
  }

  fun nameFromUuid(uuid: UUID): String {
    return requestDataFromSessionServer(uuid.toString()).name
  }

  private fun requestDataFromProfileServer(name: String) = MojangServerRequest(
    PROFILE_URL + name,
    ProfileServerResponse::class.java
  ).request()

  private fun requestDataFromSessionServer(uuid: String) = MojangServerRequest(
    SESSION_URL + uuid,
    SessionServerResponse::class.java
  ).request()
}