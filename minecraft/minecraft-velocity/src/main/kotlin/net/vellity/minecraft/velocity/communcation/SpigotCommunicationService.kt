package net.vellity.minecraft.velocity.communcation

import net.vellity.minecraft.common.communication.Channels
import net.vellity.minecraft.common.communication.ObjectWithTopic
import net.vellity.minecraft.common.redisClient
import net.vellity.utils.configuration.fromJson
import net.vellity.utils.redis.SimpleReceiver

object SpigotCommunicationService {
  private val CHANNEL = Channels.SPIGOT_TO_ALL_PROXIES
  private val topicListeners = mutableMapOf<String, Listener>()

  init {
    redisClient.addListener(SimpleReceiver(CHANNEL) {
      val objectWithTopic = fromJson(it, ObjectWithTopic::class.java)
      topicListeners[objectWithTopic.topic]?.handle(objectWithTopic.json)
    })
    redisClient.pubSubAsyncCommands().subscribe(CHANNEL).get()
  }

  fun registerListener(topic: String, listener: Listener) {
    topicListeners[topic] = listener
  }
}