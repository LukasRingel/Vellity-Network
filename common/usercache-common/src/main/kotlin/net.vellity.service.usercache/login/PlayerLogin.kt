package net.vellity.service.usercache.login

import net.vellity.utils.context.Context
import java.time.Instant
import java.util.*

data class PlayerLogin(
  val id: Int,
  val context: Context,
  val player: UUID,
  val address: String,
  val date: Instant
)