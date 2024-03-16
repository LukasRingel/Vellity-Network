package net.vellity.minecraft.common.spigot.chat.synchronizer

import net.vellity.minecraft.common.identity
import net.vellity.minecraft.common.redisClient
import net.vellity.minecraft.common.serverGroupName
import net.vellity.utils.configuration.toJson
import net.vellity.utils.redis.SimpleReceiver
import org.bukkit.entity.Player

class RedisChatSynchronizer(channel: String = serverGroupName, private val callback: (SynchronizedMessage) -> Unit) : ChatSynchronizer {
  private val channelName = "network:common:chat:$channel"

  override fun publishMessage(sender: Player, message: String) {
    val payload = SynchronizedMessage(sender.uniqueId, sender.name, message, identity.id)
    redisClient.asyncCommands()
      .publish(channelName, toJson(payload))
      .whenComplete { _, _ -> }
  }

  override fun receiveMessages() {
    redisClient.addListener(SimpleReceiver(channelName) { message ->
      val payload = SynchronizedMessage.fromJson(message)
      if (payload != null) {
        callback(payload)
      }
    })
    redisClient.pubSubAsyncCommands().subscribe(channelName).get()
  }
}