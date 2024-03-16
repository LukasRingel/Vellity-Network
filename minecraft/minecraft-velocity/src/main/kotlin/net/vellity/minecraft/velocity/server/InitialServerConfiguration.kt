package net.vellity.minecraft.velocity.server

data class InitialServerConfiguration(
  val fallbackServerGroup: String,
  val accessGroupToServerGroup: Map<Int, String>,
)