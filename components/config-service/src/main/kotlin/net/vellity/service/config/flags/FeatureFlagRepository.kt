package net.vellity.service.config.flags

import net.vellity.service.config.FeatureFlag
import net.vellity.utils.context.Context
import java.time.Instant

interface FeatureFlagRepository {
  fun getFlags(): List<FeatureFlag>

  fun createFlag(context: Context, name: String, enabled: Boolean, activeUntil: Instant): FeatureFlag

  fun updateFlag(id: Int, enabled: Boolean, activeUntil: Instant)
}