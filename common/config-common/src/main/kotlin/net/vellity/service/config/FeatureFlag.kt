package net.vellity.service.config

import net.vellity.utils.context.Context
import java.time.Instant

data class FeatureFlag(
  val id: Int,
  val context: Context,
  val name: String,
  val enabled: Boolean,
  val activeUntil: Instant
)
