package net.vellity.minecraft.common.spigot.chat.synchronizer

import net.vellity.utils.configuration.fromJson
import java.util.*

data class SynchronizedMessage(
  val sender: UUID,
  val originalName: String,
  val message: String,
  val sourceServer: String
) {
  companion object {
    fun fromJson(json: String): SynchronizedMessage? {
      return fromJson(json, SynchronizedMessage::class.java)
    }
  }
}