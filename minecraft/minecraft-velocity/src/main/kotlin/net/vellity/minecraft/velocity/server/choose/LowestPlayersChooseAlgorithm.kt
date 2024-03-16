package net.vellity.minecraft.velocity.server.choose

import com.velocitypowered.api.proxy.server.RegisteredServer

class LowestPlayersChooseAlgorithm: ChooseInitialServerAlgorithm {
  override fun best(list: List<RegisteredServer>): RegisteredServer? {
    return list.minByOrNull { server -> server.playersConnected.size }
  }
}