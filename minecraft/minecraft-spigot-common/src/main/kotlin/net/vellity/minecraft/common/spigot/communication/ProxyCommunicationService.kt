package net.vellity.minecraft.common.spigot.communication

import net.vellity.minecraft.common.communication.Channels
import net.vellity.minecraft.common.communication.ObjectWithTopic
import net.vellity.minecraft.common.redisClient

object ProxyCommunicationService {
  private val CHANNEL = Channels.SPIGOT_TO_ALL_PROXIES

  fun sendToAllProxiesOfCurrentContext(topic: String, any: Any) {
    redisClient.asyncCommands().publish(CHANNEL, ObjectWithTopic.toJson(topic, any)).get()
  }
}