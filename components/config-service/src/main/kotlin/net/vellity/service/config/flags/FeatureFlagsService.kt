package net.vellity.service.config.flags

import net.vellity.service.config.FeatureFlag
import net.vellity.utils.context.Context
import java.time.Instant

interface FeatureFlagsService {
  fun getFlags(context: Context): List<FeatureFlag>

  fun getFlag(context: Context, name: String): FeatureFlag?

  fun isFlagActive(context: Context, name: String, default: Boolean): Boolean

  fun createFlag(context: Context, name: String, enabled: Boolean, activeUntil: Instant): FeatureFlag

  fun updateFlag(context: Context, name: String, enabled: Boolean, activeUntil: Instant): FeatureFlag
}