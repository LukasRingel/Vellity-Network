package net.vellity.minecraft.common.player

import java.util.UUID

data class ServerChangeToExactIdRequest(
  val player: UUID,
  val serverId: String
) {
   companion object {
      const val TOPIC = "server-change-to-exact-id"
   }
}