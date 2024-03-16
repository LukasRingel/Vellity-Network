package net.vellity.service.explorer

import net.vellity.utils.context.Context

data class Identity(
  val id: String,
  val context: Context,
  val type: String,
  val hostname: String,
  val port: Int
)