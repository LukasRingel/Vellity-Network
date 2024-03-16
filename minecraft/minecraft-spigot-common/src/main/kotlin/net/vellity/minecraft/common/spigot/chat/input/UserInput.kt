package net.vellity.minecraft.common.spigot.chat.input

import java.util.*

data class UserInput(
  val player: UUID,
  val callback: (String) -> Unit
)