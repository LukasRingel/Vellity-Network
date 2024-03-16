package net.vellity.utils.redis

import io.lettuce.core.pubsub.RedisPubSubListener

class SimpleReceiver(private val channelName: String, private val callback: (String) -> Unit): RedisPubSubListener<String, String> {
  override fun message(channel: String?, message: String?) {
    if (!channel?.lowercase()?.startsWith(channelName.lowercase())!!) {
      return
    }

    if (message == null) {
      return
    }

    callback(message)
  }

  override fun message(pattern: String?, channel: String?, message: String?) {
    message(channel, message)
  }

  override fun subscribed(channel: String?, count: Long) {
    // do nothing
  }

  override fun psubscribed(pattern: String?, count: Long) {
    // do nothing
  }

  override fun unsubscribed(channel: String?, count: Long) {
    // do nothing
  }

  override fun punsubscribed(pattern: String?, count: Long) {
    // do nothing
  }
}