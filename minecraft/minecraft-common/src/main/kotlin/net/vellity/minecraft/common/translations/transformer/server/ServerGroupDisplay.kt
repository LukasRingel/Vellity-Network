package net.vellity.minecraft.common.translations.transformer.server

import net.vellity.minecraft.common.serverGroupName

class ServerGroupDisplay(private val serverGroup: String = serverGroupName.lowercase()) {
  fun getLocalServerGroupDisplay(): String {
    return serverGroup
  }

  companion object {
    fun fromIdentity(serverGroupName: String): ServerGroupDisplay {
      val strings = serverGroupName.split("-")
      if (strings.size == 1) {
        return ServerGroupDisplay(serverGroupName)
      }
      return ServerGroupDisplay(strings.subList(0, strings.size - 1).joinToString("-").lowercase())
    }

    fun local(): ServerGroupDisplay {
      return ServerGroupDisplay()
    }
  }
}