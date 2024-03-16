package net.vellity.minecraft.common.communication

import net.vellity.utils.configuration.toJson

data class ObjectWithTopic(
  val topic: String,
  val json: String
) {
  companion object {
    fun toJson(topic: String, any: Any): String {
      return toJson(ObjectWithTopic(topic, toJson(any)))
    }
  }
}