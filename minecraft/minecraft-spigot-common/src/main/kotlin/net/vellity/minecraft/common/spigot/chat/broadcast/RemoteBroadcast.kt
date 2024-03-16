package net.vellity.minecraft.common.spigot.chat.broadcast

import net.vellity.utils.configuration.fromJson

data class RemoteBroadcast(val identifier: String, val broadcastContent: BroadcastContent) {
  companion object {
    fun create(identifier: String, broadcastContent: BroadcastContent): RemoteBroadcast {
      return RemoteBroadcast(identifier, broadcastContent)
    }

    fun createFromJson(json: String): RemoteBroadcast {
      return fromJson(json, RemoteBroadcast::class.java)
    }
  }
}