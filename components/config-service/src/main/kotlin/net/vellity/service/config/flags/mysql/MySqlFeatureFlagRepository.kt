package net.vellity.service.config.flags.mysql

import net.vellity.service.config.FeatureFlag
import net.vellity.service.config.flags.FeatureFlagRepository
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class MySqlFeatureFlagRepository(private val mySqlConnection: MySqlConnection) : FeatureFlagRepository {
  override fun getFlags(): List<FeatureFlag> {
    return mySqlConnection.executeQuery(SelectAllFeatureFlags())
  }

  override fun createFlag(context: Context, name: String, enabled: Boolean, activeUntil: Instant): FeatureFlag {
    return FeatureFlag(
      mySqlConnection.executeUpdateAndGetId(CreateFeatureFlag(context, name, enabled, activeUntil)),
      context,
      name,
      enabled,
      activeUntil
    )
  }

  override fun updateFlag(id: Int, enabled: Boolean, activeUntil: Instant) {
    mySqlConnection.executeUpdate(UpdateFeatureFlag(id, enabled, activeUntil))
  }
}