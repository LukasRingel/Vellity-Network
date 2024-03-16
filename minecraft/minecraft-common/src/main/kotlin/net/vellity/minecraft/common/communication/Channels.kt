package net.vellity.minecraft.common.communication

import net.vellity.minecraft.common.context

class Channels {
  companion object {
    val SPIGOT_TO_ALL_PROXIES = "network:common:to-proxy:${context.name.lowercase()}:all"
    const val MESSAGE_BROADCAST = "network:common:chat:broadcasts"
  }
}