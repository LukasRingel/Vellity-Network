package net.vellity.service.config.flags.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class FeatureFlagsTableCreationMigration: OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries(): List<String> = listOf(
    "create table if not exists config_flags " +
      "( " +
      "    id         int auto_increment primary key, " +
      "    context    int                                 not null, " +
      "    name       varchar(36)                                 not null, " +
      "    enabled    bit       default 0                 not null, " +
      "    activeUntil timestamp default current_timestamp not null " +
      ");"
  )
}