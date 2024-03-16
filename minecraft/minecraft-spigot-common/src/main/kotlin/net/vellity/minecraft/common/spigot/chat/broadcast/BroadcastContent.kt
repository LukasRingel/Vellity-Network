package net.vellity.minecraft.common.spigot.chat.broadcast

data class BroadcastContent(
  val message: String,
  val replacements: Map<String, Any>
)