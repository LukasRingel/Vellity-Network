package net.vellity.service.config.flags.mysql

import net.vellity.service.config.FeatureFlag
import net.vellity.utils.mysql.extensions.getContext
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.query.SelectQuery
import java.sql.PreparedStatement
import java.sql.ResultSet

class SelectAllFeatureFlags: SelectQuery<FeatureFlag> {
  override fun query(): String {
    return "select * from config_flags where activeUntil > now()"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    // Nothing to replace
  }

  override fun mapper(resultSet: ResultSet): FeatureFlag {
    return FeatureFlag(
      resultSet.getInt("id"),
      resultSet.getContext("context"),
      resultSet.getString("name"),
      resultSet.getBoolean("enabled"),
      resultSet.getInstant("activeUntil")
    )
  }
}