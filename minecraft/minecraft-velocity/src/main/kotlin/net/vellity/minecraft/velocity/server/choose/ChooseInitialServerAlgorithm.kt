package net.vellity.minecraft.velocity.server.choose

import com.velocitypowered.api.proxy.server.RegisteredServer

interface ChooseInitialServerAlgorithm {
  fun best(list: List<RegisteredServer>): RegisteredServer?
}